package com.example.demo.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.demo.config.AlipayConfig;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserBuy;
import com.example.demo.service.CigaretteService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserBuyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/UserBuy")
public class UserBuyController {
    String app_id = AlipayConfig.app_id;
    String private_key = AlipayConfig.merchant_private_key;
    String notify_url = AlipayConfig.notify_url;
    String return_url = AlipayConfig.return_url;
    String url = AlipayConfig.gatewayUrl;
    String charset = AlipayConfig.charset;
    String format = AlipayConfig.format;
    String public_key = AlipayConfig.alipay_public_key;
    String signtype = AlipayConfig.sign_type;;
    @Resource
    private UserBuyService userBuyService;
    @Resource
    private OrderService orderService;
    @Resource
    private CigaretteService cigaretteService;
    @RequestMapping("/UpdateUserBuy")
    public String UpdateUserBuy(HttpSession session,
                                HttpServletResponse response){
//        AlipayConfig.address="ssss";
//        AlipayConfig.phone="10086";
        System.out.println("AlipayConfig.address:"+AlipayConfig.address);
        System.out.println("AlipayConfig.phone:"+AlipayConfig.phone);
        User user=(User) session.getAttribute("user");
        System.out.println(user.getName());
        UserBuy userbuy = null;
        List<UserBuy> list = userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),0);
        if(list.size()!=0){
            userbuy=list.get(0);
            String order=userbuy.getOrder();
            System.out.println("order:"+order);
            int i = userBuyService.UpdateUserBuy(AlipayConfig.address, AlipayConfig.phone, user.getName(),order,AlipayConfig.money);
            if(i>0){//买卖交易完成，进行更新库存操作
                List<Order> orders = orderService.SelectOrders(order, 0, 100);
                System.out.println("orders:"+orders.size());
                boolean flag=true;
                for (int j = 0; j < orders.size(); j++) {
                    Order order1 =  orders.get(j);
                    System.out.println(order1.getNumber()+order1.getCigarette());
                    int g=cigaretteService.UpdateCigaretteSurplus(order1.getNumber(),order1.getCigarette());
                    if(g<=0)flag=false;
                }
                if(flag){
                    return "redirect:/Cigarette/SelectCigarette";
                }else{
                    return  "test";
                }
            }
        }
        return  "test";
    }
    @RequestMapping("/SelectHistoryOrder")
    public String SelectHistoryOrder(HttpSession session, ModelMap model){
        User user=(User) session.getAttribute("user");
        List<UserBuy> list= userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),2);
        Map<String,List> map =new HashMap<String, List>();
        for(int i=0;i<list.size();i++){
            List<Order> order =orderService.SelectOrder(list.get(i).getOrder(),0,Integer.MAX_VALUE,2);
            System.out.println("SelectHistoryOrder:"+order.size());
            map.put(list.get(i).getOrder(),order);

        }
        System.out.println(map.isEmpty());
        model.addAttribute("map",map);
        return "HistoryOrder";
    }

    @RequestMapping("/pay")
    public void pay(@Param("address") String address,
                    @Param("phone") String phone,
                    @Param("money") String money,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    HttpSession session) throws Exception {
        // 模拟从前台传来的数据
        AlipayConfig.phone=phone;
        AlipayConfig.address=address;
        AlipayConfig.money=Double.parseDouble(money);
        User user=(User) session.getAttribute("user");
        List<UserBuy> list = userBuyService.selectUserBuyListWhereUserAndStateEQ0(user.getName(),0);
        UserBuy userbuy=null;
        if(list.size()!=0){
            userbuy =list.get(0);
        }
        String order=userbuy.getOrder();
        System.out.println("money:"+money);
        Random random = new Random();
        String orderNo = random.nextInt(10000)+""; // 生成订单号
        System.out.println("orderNo:"+orderNo);
        String totalAmount = money; // 支付总金额
        String subject = "香烟订单:"+order; // 订单名称
        String body = "抽死你"; // 商品描述
        String sessionId=request.getSession().getId();
        // 封装请求客户端
        AlipayClient client = new DefaultAlipayClient(url, app_id, private_key, format, charset, public_key, signtype);

        // 支付请求
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setProductCode("FAST_INSTANT_TRADE_PAY"); // 设置销售产品码
        model.setOutTradeNo(orderNo); // 设置订单号
        model.setSubject(subject); // 订单名称
        model.setTotalAmount(totalAmount); // 支付总金额
        model.setBody(body); // 设置商品描述
        model.setTimeoutExpress("2m");
        alipayRequest.setBizModel(model);

        String form = client.pageExecute(alipayRequest).getBody(); // 生成表单

        response.setContentType("text/html;charset=" + charset);
        response.getWriter().write(form); // 直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }
    @RequestMapping("/returnUrl")
    public String returnUrl(@Param("address") String address,
                            @Param("phone") String phone,
                            HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息（官方固定代码）
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, public_key, charset, signtype); // 调用SDK验证签名

        // 返回界面
        if (signVerified) {
            System.out.println("前往支付成功页面");
            return "redirect:/UserBuy/UpdateUserBuy?address="+address+"&phone="+phone;
        } else {
            System.out.println("前往支付失败页面");
        }
        return "Order";
    }
    @RequestMapping("/notifyUrl")
    public void notifyUrl(HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, public_key, charset, signtype); // 调用SDK验证签名

        if (signVerified) { // 验证成功 更新订单信息
            System.out.println("异步通知成功");
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            // 修改数据库
        } else {
            System.out.println("异步通知失败");
        }
    }
}
