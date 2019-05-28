package com.example.demo.controller;

import com.example.demo.pojo.Order;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserBuy;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserBuyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/Order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private UserBuyService userBuyService;
    @RequestMapping("/SelectOrder")
    public String SelectOrder( ModelMap model,HttpSession session,
                               @RequestParam (value = "index" ,required = false ,defaultValue = "0" )String index,
                               @RequestParam(value = "shuliang", required = false , defaultValue = "5") String shuliang
    ){
        int sl=Integer.parseInt(shuliang);
        int idx=Integer.parseInt(index);
        User user=(User)session.getAttribute("user");
        //System.out.println(user.getName());
        UserBuy userbuy=null;
        String order=null;
        if(userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),0).size()!=0){
            userbuy=userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),0).get(0);
            order=userbuy.getOrder();
        }
        List<Order> orders = orderService.SelectOrder(order,idx,sl,0);
        Double totalprice = 0.0d ;
        for (int i = 0; i < orders.size(); i++) {
            Order order1 =  orders.get(i);
            //System.out.println("number:"+order1.getNumber()+"price:"+order1.getPrice());
            totalprice+=order1.getPrice()*order1.getNumber();
        }
        //System.out.println(totalprice+"");
        //System.out.println(orders.size());
        model.addAttribute("orders",orders);
        model.addAttribute("totalprice",totalprice+"");
        return "Orders";
    }
    @ResponseBody
    @RequestMapping("/AddOrder")
    public HashMap<String,String> AddOrder(@RequestParam("account") String account,
                                               @RequestParam("cigaretee") String cigaretee,
                                               @RequestParam("number") String number,
                                               @RequestParam (value = "index" ,required = false ,defaultValue = "0" )String index,
                                               @RequestParam(value = "shuliang", required = false , defaultValue = "5") String shuliang,
                                               HttpSession session){
       //System.out.println("ajax 请求到达,执行AddOrder");
//      System.out.println(account);
        int sl=Integer.parseInt(shuliang);
        int idx=Integer.parseInt(index);
        HashMap<String,String> map= new HashMap<String,String>();
//        UserBuy  userbuy=(UserBuy)session.getAttribute("userbuy");
//        String order=userbuy.getOrder();
        User user=(User) session.getAttribute("user");
        UserBuy userBuys=null;
        System.out.println("session:"+user.getName());
        if(userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),0).size()!=0){
            userBuys = userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),0).get(0);
            System.out.println("AddOrder_getUserBuy_user:"+userBuys.getUser());
        }
        String order="";
        if(userBuys!=null){
            //System.out.println(userBuys.getOrder()+"gg");
            order=userBuys.getOrder();
        }
        System.out.println("AddOrder:"+order);
        if(order=="" || order==null){
            System.out.println("执行无订单开始:");
            String n_o="";
            Calendar now = Calendar.getInstance();
            String yue=null;
            if(now.get(Calendar.MONTH) + 1<10){
                yue="0"+(now.get(Calendar.MONTH) + 1);
            }
            String od= now.get(Calendar.YEAR)+""+yue+""+now.get(Calendar.DAY_OF_MONTH)+"";
//            System.out.println(od);
            Order order1 = orderService.SelectLastOrder();
            String n[];
            if(order1!=null){//数据库有订单记录
                n=order1.getOrder().split(od);
                if(n.length==1){//说明是数据库查到的最后一条记录用当前日期截不到，即数据库没有今天的数据
                    n_o=od+"01";
//                System.out.println(n_o);
                }else{//数据已经有今天的记录，在最后一条记录上加1
                    int r= Integer.parseInt(n[1])+1;
                    if(r<10){
                        n[1]="0"+r;
                    }else{
                        n[1]=r+"";
                    }
                    n_o=od+n[1];
//                System.out.println(n_o);
                }
            }else{//数据库无订单记录，直接拿当前时间构建一个order
                n_o=od+"01";
            }
            Order o=new Order();
            o.setOrder(n_o);
            o.setCigarette(account);
            o.setNumber(1);
            int i=orderService.AddOrder(o);
//            System.out.println(i);
            if(i>0){
                UserBuy userBuy=new UserBuy(user.getName(), null,null,0,user.getLocation(),n_o);
                if(order==""){//说明该用户从来没有过订单，
                    int m=userBuyService.InsertUserBuy(userBuy);
                    if(m>0){
                        session.setAttribute("userbuy",userBuy);
                        map.put("data","success");
                        //System.out.println("success");
                    }else {}
                }else{//说明该用户之前就有过订单，只是后来取消了
                    int m=userBuyService.UpdateUserBuyOrderNull(user.getName(),n_o);
                    if(m>0){//说明该用户从来没有过订单，
                        session.setAttribute("userbuy",userBuy);
                        map.put("data","success");
                        //System.out.println("success");
                    }else{ map.put("data","fail");}
                }
            }else{ map.put("data","fail");}
        }else{//用户具有订单，则先根据订单查询烟
            System.out.println("开始执行有相关订单操作：");
            List<Order> orders = orderService.SelectOrder(order,idx,sl,0);
            System.out.println("ordersize:"+orders.size());
            if(orders.size()==0){
                String n_o="";
                Calendar now = Calendar.getInstance();
                String yue=null;
                if(now.get(Calendar.MONTH) + 1<10){
                    yue="0"+(now.get(Calendar.MONTH) + 1);
                }
                String od= now.get(Calendar.YEAR)+""+yue+""+now.get(Calendar.DAY_OF_MONTH)+"";
                Order oo = orderService.SelectLastOrder();
                String n[];
                if(oo!=null){//数据库有订单记录
                    System.out.println("oo:"+oo.getOrder());
                    n=oo.getOrder().split(od);
                    System.out.println(od);
                    if(n.length==1){//说明是数据库查到的最后一条记录用当前日期截不到，即数据库没有今天的数据
                        n_o=od+"01";
                   System.out.println(n_o);
                    }else{//数据已经有今天的记录，在最后一条记录上加1
                        int r= Integer.parseInt(n[1])+1;
                        if(r<10){
                            n[1]="0"+r;
                        }else{
                            n[1]=r+"";
                        }
                        n_o=od+n[1];
                    }
                }else{//数据库无订单记录，直接拿当前时间构建一个order
                    n_o=od+"01";
                }
                System.out.println("n_o:"+n_o);
                Order o=new Order();
                o.setOrder(n_o);
                o.setCigarette(account);
                o.setNumber(1);
                int i=orderService.AddOrder(o);
                if(i>0){
                    UserBuy userBuy=new UserBuy(user.getName(), null,null,0,user.getLocation(),n_o);
                    int m=userBuyService.InsertUserBuy(userBuy);
                    if(m>0){
                        session.setAttribute("userbuy",userBuy);
                        map.put("data","success");
                        //System.out.println("success");
                    }else {}
                    return map;
                }
            }
            for (int i = 0; i < orders.size(); i++) {
                Order order1 =  orders.get(i);
                System.out.println(order1.getCigarette());
               System.out.println("c:"+account);
                if(order1.getCigarette().equals(account)){
                    System.out.println("if");
                    Order op=new Order();
                    op.setNumber(order1.getNumber()+1);
                    op.setCigarette(account);
                    op.setOrder(order);
                    int k=orderService.UpdateCigaretteNumber(op);
                    if(k>0){
                        map.put("data","success");
                    }
                    return map;
                }
            }
            Order o=new Order();
            o.setOrder(order);
            o.setCigarette(account);
            o.setNumber(1);
            int i=orderService.AddOrder(o);
            if(i>0){
                map.put("data","success");
                //System.out.println("success");
            }else{ map.put("data","fail");}
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/UpdateCigaretteNumber")
    public  HashMap<String,String> UpdateCigaretteNumber(@RequestParam("number") int number,
                                                         @RequestParam("order") String order,
                                                         @RequestParam("cigarette") String cigarette
    ){
        System.out.println("开始执行更新数量'");
        HashMap<String,String> map=new HashMap<String,String>();
        Order o=new Order();
        o.setOrder(order);
        o.setCigarette(cigarette);
        o.setNumber(number);
        int i=orderService.UpdateCigaretteNumber(o);
        if(i>0){
            map.put("data","success");
            //System.out.println("success");
        }else{ map.put("data","fail");}
        return map;
    }

    @ResponseBody
    @RequestMapping("/DeleteOrderCigarette")
    public  HashMap<String,String> DeleteOrderCigarette(@Param("order") String order,@Param("cigarette") String cigarette,
                                                        @RequestParam (value = "index" ,required = false ,defaultValue = "0" )String index,
                                                        @RequestParam(value = "shuliang", required = false , defaultValue = "5") String shuliang,
                                                        HttpSession session){
        HashMap<String,String> map=new HashMap<String, String>();
        int sl=Integer.parseInt(shuliang);
        int idx=Integer.parseInt(index);
        int i = orderService.DeleteOrderCigarette(order, cigarette);
        User user = (User) session.getAttribute("user");
        UserBuy userBuy=(UserBuy) session.getAttribute("userbuy");
        if(i>0){
            List<Order> orders = orderService.SelectOrder(order,idx,sl,0);
            //判断list的长度，如果为0 update userbuy.order==null
            if(orders.size()==0){
                int i1 = userBuyService.UpdateUserBuyOrderNull(user.getName(),null);
                if(i1>0){
                    map.put("data","success");
                    map.put("length",orders.size()+"");
                }else{
                    map.put("data","fail");
                }
            }else{
                map.put("data","success");
                map.put("length",orders.size()+"");
            }
        }else{
            map.put("data","fail");
        }
        return map;
    }
}
