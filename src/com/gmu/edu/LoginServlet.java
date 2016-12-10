package com.gmu.edu;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doPerform(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doPerform(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPerform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		Login login=new Login();
		login.setEmail(request.getParameter("email"));
		login.setPassword(request.getParameter("password"));
		
		LoginDao loginDao=new LoginDao();
		String status=loginDao.loginUser(login);
		if(status.equals("SUCCESS"))
		{
			HttpSession session=request.getSession();
			if (session.isNew()) {
				System.out.println(">>LoginServlet session is new");
				System.out.println("LoginServlet session "+session.getAttribute("username"));
				if(session.getAttribute("username")==null || session.getAttribute("username").equals(null) || !session.getAttribute("username").equals(login.getEmail())) {
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("/ERROR.jsp");
					requestDispatcher.forward(request, response);
				}
			} else {
				System.out.println(">>LoginServlet session is old");
				System.out.println(">>LoginServlet "+session.getAttribute("username"));
			}
			session.setAttribute("username", login.getEmail());
			session.setMaxInactiveInterval(20);
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("/user.jsp");
			requestDispatcher.forward(request, response);
		}
		if(status.equals("FAIL"))
		{
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("/ERROR.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
