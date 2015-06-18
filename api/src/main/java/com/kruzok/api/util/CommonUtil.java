package com.kruzok.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


public class CommonUtil {
    
    public static final int MAX_COLLECTION_SIZE = 10;
    
    public static final int MAX_IDS_COLLECTION_SIZE = 100;
    
    public static <T extends Object> void normalizeListBySize(List<T> list) {
        normalizeListBySize(list, MAX_COLLECTION_SIZE);
    }
    
    public static <T extends Object> void normalizeListBySize(List<T> list, int size) {
        if (CollectionUtils.isNotEmpty(list) && list.size() > size) {
            // we need unique list of values
            Set<T> set = new LinkedHashSet<T>(list);
            list.clear();
            int idx = 0;
            for (T item : set) {
                if (idx >= size) {
                    break;
                }
                list.add(item);
                idx++;
            }
        }
    }
    
    public static List<Long> stringToLongList(String str, String delimeter) {
        List<String> terms = Arrays.asList(StringUtils.trimToEmpty(str).split(delimeter));
        Set<Long> contactsId = new HashSet<Long>(terms.size());
        for (String term : terms) {
            try {
                if (StringUtils.isNumeric(term)) {
                    contactsId.add(Long.parseLong(term));
                }
            }
            catch (Exception e) {
                
            }
        }
        return new ArrayList<Long>(contactsId);
    }
    
}
