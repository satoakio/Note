package org.java.core.base.collection.hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

// If the specified key is not present or is associated with null, 
//then associates it with the given non-null value. Otherwise, 
//replaces the associated value with the results of the given 
//remapping function, or removes if the result is null.
//���ָ���ļ������ڻ���null����������������ķǿ�ֵ������� ����
//�����ֵ�滻Ϊ������ӳ�亯���Ľ��������������Ϊnull��ɾ����
public class HashMapMerge{

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", "2");
		map.put(null, "10");
		map.put("10", null);

		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			//merge throws NullPointerException if key or value is null
			if(key != null && value != null) 
			map.merge(entry.getKey(), entry.getValue(), 
					(k, v) -> {return k + v;});
		}
		System.out.println(map);
		
		map.merge("5", "5", (k, v) -> {return k + v;}); // key not present
		System.out.println(map);
		
		map.merge("1", "1", (k, v) -> {return null;}); // method return null, so remove
		System.out.println(map);

	}

}