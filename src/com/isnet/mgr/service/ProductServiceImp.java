package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.ProductDao;

@Service
public class ProductServiceImp implements ProductService {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired 
	ProductDao productDao; 

	@Override
	public List<Map<String, Object>> getProductList() {
		List<Map<String, Object>> resultList = productDao.select_product_01();
		return resultList;
	}

	@Override
	public void add_product(Map<String, Object> paramMap) {
		productDao.add_product(paramMap);
	}

	@Override
	public List<Map<String, Object>> getProductList02(Map<String, Object> paramMap) {
		return productDao.select_product_02(paramMap);
	}

}
