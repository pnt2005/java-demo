package com.example.demo.utils;

import java.util.Map;

public class MapUtil {
	public static <T> T getObject(Map<String, Object> params, String key, Class<T> tClass) {
		Object obj = params.getOrDefault(key, null);	//lấy giá trị từ params với key, nếu không có thì mặc định là null
		if (obj != null) {
			if (tClass.getTypeName().equals("java.lang.Long")) {
				obj = obj != "" ? Long.valueOf(obj.toString()) : null;	//Object -> String -> Long
			} else if (tClass.getTypeName().equals("java.lang.Integer")) {
				obj = obj != "" ? Integer.valueOf(obj.toString()) : null;
			} else if (tClass.getTypeName().equals("java.lang.String")) {
				obj = obj.toString();
			}
			return tClass.cast(obj); // ép kiểu cho obj (ví dụ: obj mang giá trị Long do ở trên nhưng kiểu của obj vẫn là Object 
		}
		return null;
	}

}
