package com.example.demo.config;

public class AlipayConfig {
    public static String address;
    public static String phone;
    public static double money;
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
    public static String return_url = "http://localhost:8080/thymeleaf/UserBuy/returnUrl";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/thymeleaf/UserBuy/notifyUrl";
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016100100636458";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA60qprc07AyGOLYTj6PYNUfjtvM0sPobCqJRBCPopcL8+J/Wuhh92nnkQ5LvI//o7MFvT4IxfS7lUlmA2YBgvHcS8hXJKZfv0TFbFPMTrFd2ClnEOJ84bc44DILpQuAKpK8UQYjJXFAAZjd4EkW9/nTRxSWntFVFsdVxsvdoekx+VSLmlJaHK5THgWPl39t4MWrvxoe5YXg9QUvs5ChChCYO1gFLDlfpqkQJHmoIgYtPmdND0T4vwhHBM0j3pXgyAxPOMrD3Nh9ZxNpcTXzKtQ7YncwRy3J9jTye69X8ZOg//e9CbpbdW4oKrtXcHC/5SwtiFW9vdRhFvYl3lJ07R5wIDAQAB";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDsM1vZzVkaI4DrSlPmDAULRQinKWqDP2j54GNsvg9i6TfdMJhEfBGt7xNttwTtHsZGArOnpgNAnadIyvQjmchfS09s4LcMFuCDKaASpyUHMKFsFUL5rzvRRDliw2TPJYF6L0oZvV7PeEpi3tP/Obk94rSbHUaDucZ9EL0ZJZhgyHl8Zk0BnQ+bTPpBhRXrE2MOmxF91T+oxMtqJuzNWsuLWR3oPqC3SWdL6C++42nYzzd8CdvzoYmrIFGpNjV2fUTJrllLQMoMyGyxPxYVnYBNEtgF7ipQ5KzS/mceeY0kIogPDgLw7Jb6JaXwQ5hC7Jwfz9bz9je10nVE9p7rmvFAgMBAAECggEAaB2Sbi9lERtwnAt9i8ZAzNLPBoZem9Flfv57ePI0PUR4d4qyW1h5ZQ/CupaF7LZWVIKjmLPedvqpmJbD/0F23A1BhXS1UyyQTIN/6BIVNSx/xn6g3qXwXiN2tmR3uR6/ZbCaTKUKFAPaTqROO/ExA3mKsc65zn5VzDlH7jzQgAquqihjTtNj/QY4kdQM5S6DHwzO3AmIfxIB6hhJ0+BLmeyI3hLZ1VziOfSk2knOicpDoVgm/cXIRheLCGJP0LYR1wotqyxzWEJySmQOcl/PIZgxDEjkDXifahiJQBkEinUeGuJ02uZGa9LfI95j7iwpJXKXuCB4Wj2A1I+PD+WhCQKBgQDONPCNuK9vuj9V7mTg7ltT+ue6Uvh+/GGiIlqN44NBvIUF0PM0dAIVwNlg6pV+bDuiuQZHhyNZ1bwSqqrHUFsNisIrOlNqshEPbAgt1PwTiTQFXNO0Ypo4xdLYVEc8p982DMEVFXXmZdUspHp//P/S8JZ2jIK8xQq4gsXc3zKvpwKBgQCjfYGirDDKvz1nH8VsPmJVzcI1Uc52oS1jHFl3q8atr972CuNzTy5EJkGQOJBtYk0deyuVv6MH0IUnKBHw7+Z4Pep0vtg4Lrh0iKfBRQ8Pd5RaveXartlFrUh/Go3OXFqlJ16jvbJz+dMNB+kWjhoBx87yyu7CQ7odFcnWXVjWswKBgBkr3TFvmfrJgXG76BSNBBj8R802056ssD5aczNe0mbz3EkZeJaukpKVN/PcruL/00fJzjptBKOys4KT0sUByhudFJayR/x+QBbh2R2/IgZAeVkuO6mJX8oOW+6MWyXMWDMA25D3Ta0SQTNx4II/H/tf4v4VqN/iLazYSc7scAOBAoGAKU5WaHE0yqTSsLFYQebpFWCtBqeqkFJck0PgXtnpkoxyups5qDKDL/iya+u+ocRKU36BHo6SdDD8003Oap9SDWMnT2PAYUF8nQTBzZTTH0dJFjwQTNYMUwxa2rIszcyLolp2EMymv6KI7ivneilg7L48zR70f+R4q0yL+DsRbP0CgYA9Kg/7rz6jAEUPa79T1B0JtgFDtxQrdFy19FUyNgOhYEcRWuMMTB3fhsrNNZ0wmLEe0zpWXpFZC5HsXH6AWuN1PJnGxc33YRopo4Jl5e5THk2ICdJtdTwZtXxiKCJnhTdfIZ4ZCp0WIR4foCb4xLkMYhWul2c7fR204GBgvYRD4Q==";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    public static String format = "json";
}
