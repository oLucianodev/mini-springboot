package io.isiflix.isispring.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.google.gson.Gson;

import io.isiflix.isispring.datastructures.ControllerMaps;
import io.isiflix.isispring.datastructures.ControllersInstances;
import io.isiflix.isispring.datastructures.DependencyInjectionMap;
import io.isiflix.isispring.datastructures.RequestControllerData;
import io.isiflix.isispring.datastructures.ServiceImplementationMap;
import io.isiflix.isispring.util.IsiLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IsiDispatchServlet extends HttpServlet{
	//ignorando o favicon
	
	
	private static final long serialVersionUID =1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
	      throws IOException, ServletException{
		if(request.getRequestURL().toString().endsWith("/favicon.ico")) {
			return;
		}
		PrintWriter out = new PrintWriter (response.getWriter());
		Gson gson = new Gson();
		String url = request.getRequestURI();
		System.out.println(url);
		String httpMethod = request.getMethod().toUpperCase();
		String key = httpMethod + url;
		RequestControllerData data = ControllerMaps.values.get(key);
		IsiLogger.log("IsiDispatchServlet", "URL:"+ url+"("+ httpMethod +")- Handler"+ data.getControllerMethod());
		
		Object controller;
		IsiLogger.log("DiaspatchServlet","SearChing for Controller Instance" );
		// vendo se tenho instancia
		try {
			controller = ControllersInstances.instances.get(data.controllerClass);
		if ( controller == null) {
			IsiLogger.log("DiaspatchServlet","Creating new Controller Instance" );
			
			controller = Class.forName(data.controllerClass).getDeclaredConstructor().newInstance();
			ControllersInstances.instances.put(data.controllerClass, controller);
			
			injectedDependencies(controller);
		}
		
		// extraindo metodo
		Method controllerMethod =null;
		for (Method method: controller.getClass().getMethods()) {
			if(method.getName().equals(data.controllerMethod)) {
				controllerMethod = method;
				break;
			}
			}
		
		// excutando metodo
        IsiLogger.log("DispatcherServelet","Invoking method" + controllerMethod.getName() + " to handle request" );
		
		
		// meu metodo tem parametros?
		if ( controllerMethod.getParameterCount()> 0) {
			IsiLogger.log("IsiDispatchServlet", "Method " + controllerMethod.getName()+ "Has Paracmeted");
			Object arg;
			Parameter parameter =  controllerMethod.getParameters()[0];
			if (parameter.getAnnotations()[0].annotationType().getName().equals("io.isiflix.isispring.annotations.IsiBody")) {
				// preciso ler os dados da requisissao 
				String body = readBytesFromRequest(request);
				IsiLogger.log("", "     Found Parameter from request of type "+ parameter.getType().getName());
			    IsiLogger.log("", "  Parament content" + body);
			   arg = gson.fromJson(body, parameter.getType());
			   
			   out.println(gson.toJson(controllerMethod.invoke(controller, arg)));
			}
			}
		else {
			 out.write(gson.toJson(controllerMethod.invoke(controller)));
			}
		

		
		
		out.close();
		
		
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
		
		
		
	private String readBytesFromRequest(HttpServletRequest request)throws Exception {
		StringBuilder str = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		while ((line = br.readLine())!= null) {
			str.append(line);
				}
		return str.toString();
	}
	private void injectedDependencies(Object client ) throws Exception {
		for(Field attr: client.getClass().getDeclaredFields()) {
			String attrType = attr.getType().getName();
			IsiLogger.log("IsiDIspatcherServlet", "injected " + attr.getName() + "Field has type" + attrType);
			   Object serviceImpl;
			   if (DependencyInjectionMap.objects.get(attrType)== null) {
				   // trm pela declaracao da interface
				   IsiLogger.log("DependencyInjection", "Couldn't find Instance for " + attrType);
				   String implType = ServiceImplementationMap.implementations.get(attrType);
				 
				   // preciso buscar pela declaracao da imple
				   if (implType != null) {
					   IsiLogger.log("DependencyInjection", "Found Instance for " + implType);
					   // vnedo se a declaracao tem instancia 
					   serviceImpl = DependencyInjectionMap.objects.get(implType); 
					
					   
					   // se nao tiver eu crio um novo objeto 
					   if (serviceImpl == null) {
						   IsiLogger.log("DependencyInjection","Injection new Object");
						   serviceImpl = Class.forName(implType).getDeclaredConstructor().newInstance();
						   DependencyInjectionMap.objects.put(implType, serviceImpl);
								   
					   }
					   
					   // agora preciso atribuir essa intsancia ao atributo
					   attr.setAccessible(true);
					   attr.set(client, serviceImpl);
					   IsiLogger.log("DependencyInjection", "Injected Object sucessfully");
				   }
		
	}
	}
}
}
