package com.dj.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import com.dj.entity.EnvData;
import com.dj.entity.User;

/**
 * Servlet implementation class Login_Servlet
 */
@WebServlet("/Login_Servlet")
public class Login_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    List<User> users; 
    List<EnvData> envDatas;
    User user;
    EnvData envData;
    
    private static final String NAME="name";//键名定义成常量
    private static final String PASSWORD="password";//键名定义成常量
    
    private static final String GOATHOUSEID="goatHouseID";//键名定义成常量
    private static final String DEVICE = "device";
    private static final String ONOFF = "onOff";
    
    private static final String TEMLOW = "temlow";
    private static final String TEMHIGH = "temhigh";
    private static final String HUMLOW = "humlow";
    private static final String HUMHIGH = "humhigh";
    private static final String CHECKED = "checked";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login_Servlet() {
        super();
       //先把数据放在这里
        
        envDatas = new ArrayList<>();
        envDatas.add(new EnvData("0","20.0","70.0","10.0","30.0"));//温度，湿度，氨气浓度，空气流速
        envDatas.add(new EnvData("1","21","71","11","31")); 
        envDatas.add(new EnvData("2","22","72","12","32"));
        envDatas.add(new EnvData("3","23","73","13","33"));
        envDatas.add(new EnvData("4","24","74","14","34"));
        envDatas.add(new EnvData("5","25","75","15","35"));
        envDatas.add(new EnvData("6","26","76","16","36"));
        envDatas.add(new EnvData("7","27","77","17","37"));
        
        
        users = new ArrayList<>();
        user  = new User("高振",'男',24,1.80,"123456");
        users = new ArrayList<>();
        users.add(user);
        users.add(new User("倩楠",'女',24,1.68,"123456"));
        users.add(new User("山东",'男',23,1.75,"123456"));
        users.add(new User("大振",'男',13,1.60,"123456"));
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String goatHouseID = request.getParameter(GOATHOUSEID);
		//判空
		if(goatHouseID==null) {//如果ID为空，直接退出
			System.out.println("ID为空");
			return;
		}
		//如果不为空则继续下面的处理
		for(int i=0;i<envDatas.size();i++) {
			EnvData envData = envDatas.get(i);
			if(goatHouseID.equals(envData.getID())) {//如果用户名和密码相等，就把user对象发送给客户端
				//用json格式发送 执行下列代码前要先把两个相关jar包放在该放的位置
				 ObjectMapper om = new ObjectMapper();
				 om.writeValue(response.getOutputStream(), envData);//把envData对象通过输出流发送给客户端，是通过json格式发送的
				 System.out.println(envData.toString()); //打印一下envData
			}
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//			doGet(request,response);
		String goatHouseID = request.getParameter(GOATHOUSEID);
		String device = request.getParameter(DEVICE);
		String onOff = request.getParameter(ONOFF);
		
		if(goatHouseID == null){
//			System.out.println("goatHouseID=null");
			
		}else{
			System.out.println(goatHouseID);
		}
		
		if(device == null){
//			System.out.println("device=null");
		}else{
			System.out.println(device);
		}
		
		if(onOff == null){
//			System.out.println("onOff=null");
		}else{
			System.out.println(onOff);
		}
		
		
		
		
		if(goatHouseID != null && device != null && onOff != null){//如果接收到设备和开闭
			 //用json格式发送 执行下列代码前要先把两个相关jar包放在该放的位置
			 ObjectMapper op = new ObjectMapper();
			 op.writeValue(response.getOutputStream(), "命令已执行");//把envData对象通过输出流发送给客户端，是通过json格式发送的
			 System.out.println("命令已执行: "+"羊舍ID="+goatHouseID+" 设备="+device+" 是否打开="+onOff); //打印一下envData
		}else {
//			System.out.println("完蛋，有空值");
		}
		
		
		
		//第二波
		
		String temlow = request.getParameter(TEMLOW);
		String temhigh = request.getParameter(TEMHIGH);
		String humlow = request.getParameter(HUMLOW);
		String humhigh = request.getParameter(HUMHIGH);
		
		//进行非空检查    
        if(temhigh == null || temlow == null || humhigh == null || humlow == null){
        	System.out.println("输入的信息包含空值，检查一下吧。");
            return;
        }
        System.out.println("收到的信息："
        		+" temlow="+temlow
        		+" temhigh="+temhigh
        		+" humlow="+humlow
        		+" humhigh="+humhigh);
		
		
		
		//		request.setCharacterEncoding("utf-8");//设置编码格式，解决中文乱码问题
//		String name = request.getParameter(NAME);
//		List<User> list = new ArrayList<>();
//		for(User user : users) {
//			if(user.getName().indexOf(name)>=0) {//name出现在user.getName()里了，并且出现的位置大于等于0
//				list.add(user);//就把user加到list集合里去
//			}
//		}
//		ObjectMapper om = new ObjectMapper();
//		om.writeValue(response.getOutputStream(), list);//通过json发送list
//		System.out.println("查询结束"); 
	}

}
