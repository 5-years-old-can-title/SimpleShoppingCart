package com.shopping.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ShoppingCardServlet")
public class ShoppingCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		// 從session拿出暫存的購物車資料
		HttpSession session = req.getSession();
		Map<String, Integer> myCard = (Map) session.getAttribute("myCard");

		// 購物車資料為null的時候先實體化
		if (myCard == null) {
			myCard = new HashMap<String, Integer>();
			session.setAttribute("myCard", myCard);
		}

		// 取得所有req的parameter，動態的做迭代
		Map<String, String[]> parameterMap = req.getParameterMap();
		Set<String> keySet = parameterMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			// 某物品在session購物車中紀錄的數量
			String key = iterator.next();
			Integer inCardQuantity = myCard.get(key);
			Integer newQuantity = 0;
			
			try {
				// 某物品要新增的數量
				String[] values = parameterMap.get(key);
				String value = values[0];
				Integer addQuantity = String2Integer(value);
				
				// 如果某物品目前沒有在購屋車內則設定目前數量為0
				if (inCardQuantity == null) {
					inCardQuantity = 0;
				}
	
				// 做加總，假設超出Integer最大或最小範圍則顯示錯誤訊息並且不做任何加減
				newQuantity = addQuantity + inCardQuantity;
			} catch (Exception e) {
				e.printStackTrace();
				newQuantity = inCardQuantity;
				req.setAttribute("errMsg", "數量超過最大或最小限制");
			}

			// 不允許負數，如果是負數則設定為0
			if (newQuantity < 0) {
				newQuantity = 0;
			}

			// 更新購物車
			myCard.put(key, newQuantity);
		}

		System.out.println("最新購物車內容物: " + myCard);
		req.getRequestDispatcher("/shoppingCard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * 將字串或null轉成數字
	 * 
	 * @param number
	 * @return
	 */
	private Integer String2Integer(String number) {
		if (number == null || "".equals(number.trim())) {
			return 0;
		}

		return Integer.valueOf(number);
	}

}