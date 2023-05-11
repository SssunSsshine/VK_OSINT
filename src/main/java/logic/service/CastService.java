package logic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastService {
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    public static <T, G> Map<T, List<G>> castMap(Object obj, Class<T> tClass, Class<G> gClass) {
        Map<T, List<G>> result = new HashMap<>();
        if (obj instanceof Map<?, ?>) {
            ((Map<?, ?>) obj).forEach((key, value) -> result.put(tClass.cast(key), castList(value, gClass)));
            return result;
        }
        return null;
    }
}
