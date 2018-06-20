import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project.Hotel;
import project.HotelReservation;
import project.HtmlLoader;
import project.MySQLDataStore;


public class ViewHotel extends HttpServlet {
	/*private static final long serialVersionUID = 1L;
       
    
    public ViewHotel() {
        super();
       
    }*/

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ServletContext sc = request.getServletContext();
		String headerHtml = sc.getRealPath("header.html");
		String storeManagerHeaderHtml = sc.getRealPath("storeManagerHeader.html");
		HtmlLoader loader = new HtmlLoader();
		
		String city = null, checkinDate = null, checkoutDate=null, rooms=null, roomType=null, hname=null;
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		
		HttpSession session = request.getSession();
		String role = (String)request.getSession().getAttribute("role");
		if(userId != null){
			if(role.equals("customer")){
				out.println(loader.readFile(headerHtml));
				city = request.getParameter("city");
				hname = request.getParameter("hotelName");
				if(hname == null){
					hname = (String) request.getSession().getAttribute("hname");
				}
				checkinDate = request.getParameter("checkinDate");
				checkoutDate = request.getParameter("checkoutDate");
				rooms = request.getParameter("rooms");
				roomType = request.getParameter("roomType");
				session.setAttribute("checkinDate", checkinDate);
				session.setAttribute("checkoutDate", checkoutDate);
				session.setAttribute("roomType", roomType);
				session.setAttribute("rooms", rooms);
				session.setAttribute("hname", hname);
				session.setAttribute("city", city);
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date checkInDate = null, checkOutDate = null;
				
				
				
				//int difference = dateDifference(checkInDate, checkOutDate);
				//session.setAttribute("difference", difference);
				MySQLDataStore mysql = new MySQLDataStore();
				HashMap<String, List<Hotel>> hashHotel = new HashMap<String, List<Hotel>>();
				hashHotel = mysql.getHotelInfo(hname);
				for(Map.Entry<String,List<Hotel>> map : hashHotel.entrySet())
				{
					for(Hotel list : map.getValue()){
						out.println("<div class='Summary'>");
						out.println("<div class='wrap'>");
						out.println("<div class='reservation'>");
						out.println("<form action='ViewHotel'>");
						out.println("<ul>");
						out.println("<li class='span1_of_1 left'>");
						out.println("<h5>City</h5>");
						out.println("<input name='city' class='form-control' type='text' value ='"+list.getCity()+"'>");
						out.println("</li>");
						out.println("<li class='span1_of_1 left'>");		
						out.println("<h5>Check in Date</h5>");
						out.println("<div class='checkinDate'>");
						if(checkinDate == null){
							out.println("<input name='checkinDate' class='form-control' placeholder='dd/mm/yyyy' type='text' value=''>");
						}
						if(checkinDate != null ){
							out.println("<input name='checkinDate' class='form-control' placeholder='dd/mm/yyyy' type='text' value='"+checkinDate+"'>");
						}
						out.println("</div>");
						out.println("</li>");
						out.println("<li class='span1_of_1 left'>");		
						out.println("<h5>Checkout Date</h5>");
						out.println("<div class='checkoutDate'>");
						if(checkoutDate == null){
							out.println("<input name='checkoutDate' class='form-control' placeholder='dd/mm/yyyy' type='text' value=''>");
						}
						if(checkoutDate != null ){
							out.println("<input name='checkoutDate' class='form-control' placeholder='dd/mm/yyyy' type='text' value='"+checkoutDate+"'>");
						}
						
						out.println("</div>");
						out.println("</li>");
						out.println("<li class='span1_of_1 left'>");
						out.println("<h5>Rooms</h5>");
						if(rooms == null){
							out.println("<input name='rooms' class='form-control' type='text' value=''>");
						}
						if(rooms != null ){
							out.println("<input name='rooms' class='form-control' type='text' value='"+rooms+"'>");
						}
						out.println("</li>");
						out.println("<li class='span1_of_1'>");
						out.println("<h5>Room type</h5>");
						if(roomType == null){
							out.println("<select class='form-control' id='country' name='roomType' value=''>");
						}
						if(roomType != null ){
							out.println("<select class='form-control' id='country' name='roomType' value='"+roomType+"'>");
						}
						out.println("<option>Standard Single Room</option>");
						out.println("<option>Suite room</option>");
						out.println("<option>Single room</option>");
						out.println("<option>Double room</option>");
						out.println("<select>");
						out.println("</li>");
						out.println("<li class='span1_of_3'>");
						out.println("<div class='date_btn'>");
						out.println("<input type='submit' class='btn btn-lg btn-default' value='Edit Search' />");
						out.println("</div>");
						out.println("<div class='clearfix'></div>");
						out.println("</ul>");
						out.println("</form>");
						out.println("</div>");
						out.println("</div>");
						out.println("</div>");
						
					}
				}
				
				
				out.println("<div class='wrap'>");
				out.println("<div class='gallerys'>");
				out.println("<div class='gallery-grids'>");
				
					for(Map.Entry<String,List<Hotel>> map : hashHotel.entrySet())
					{
						int i =0;
						out.println("<table>");
						for(Hotel list : map.getValue()){
							out.println("<div class='gallery-grid'>");
							
							out.println("<form action='ConfirmBooking'  class='FontColor' >");
							if(i%4 ==0){
				        		out.println("<tr>");
				        	}
				        	out.println("<td>");
				        	out.println("<h2><span>Hotel id: "+list.getHotelId()+"</h2></span>" + "<br/>");
				        	out.println("<span>Hotel Name: "+list.getHotelName() + "<span>" + "<br/>");
				        	out.println("<span>located: "+list.getCity()+ "<span>" + "<br/>");
				        	out.println("<span>Hotel Price: "+list.getPrice() + "<span>" + "<br/>");
				        	out.println("<img style='width:65%' src='"+list.getImages()+ "' alt=''><br/><br/>");
				        	out.println("<a href='BookHotelServlet?id="+list.getHotelId()+"'"+"><input type='Button' class='FontColor' value='Book'>");
				        	out.println("</a>");
				        	out.println("</form>");
				        	out.println("<form  method = 'get' action = 'ReviewServlet'>");
							out.println("<input class = 'FontColor' type = 'submit' name = 'submit' value = 'Write Review'>");
							out.println("<input type = 'hidden' name='HotelId' value='"+list.getHotelId()+"'>");
							out.println("<input type = 'hidden' name='HotelName' value='"+list.getHotelName()+"'>");
							out.println("<input type = 'hidden' name='HotelPrice' value='"+list.getPrice()+"'>");
							out.println("</form>");

							out.println("<form method = 'get' action = 'ViewReview'>");
							out.println("<input class = 'FontColor' type = 'submit' name = 'submit' value = 'View Reviews'>");
							out.println("<input type = 'hidden' name='HotelName' value='"+list.getHotelName()+"'>");
							out.println("</form>");
							out.println("</td>");
							out.println("</td>");
				        	i++;
				        	if(i%4==0){
				        		out.println("</tr>");
				        		i=0;
				        	}
			          		out.println("</div>");
						}
						out.println("</table>");
					}
		}
			else{
				response.sendRedirect("login.html");
			}
		
	}
			
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		
		
	}

	

}
