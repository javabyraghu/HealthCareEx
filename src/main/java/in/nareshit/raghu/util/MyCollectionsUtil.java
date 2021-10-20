package in.nareshit.raghu.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//JDK 8 (static methods + default methods allowed)
public interface MyCollectionsUtil {

	public static Map<Long, String> convertToMap(List<Object[]> list) {
		//Java 8 Stream API
		Map<Long,String> map =
				list
				.stream()
				.collect(Collectors.toMap(
						ob->Long.valueOf(
								ob[0].toString()), 
						ob->ob[1].toString()));
		return map;
	}

	public static Map<Long, String> convertToMapIndex(List<Object[]> list) {
		//Java 8 Stream API
		Map<Long,String> map =
				list
				.stream()
				.collect(Collectors.toMap(
						ob->Long.valueOf(
								ob[0].toString()), 
						ob->ob[1].toString()+" "+ob[2].toString()));
		return map;
	}

}
