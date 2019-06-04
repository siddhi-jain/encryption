package com.example.demo.Encryption;

import com.example.demo.reflection.Node;
import com.example.demo.reflection.ReflectionCache;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

public class Crypt {
    private static final Logger LOG = LoggerFactory.getLogger(Crypt.class);

    private static Map<Class,Integer> cyclicClassReference = new HashMap<>();

    public static void cryptFields(Object o, Node node, Function<Object, Object> crypt) {
        try {
            switch (node.type) {
                case MAP:
                    cryptMap((Document) o, node, crypt);
                    break;

                case DOCUMENT:
                case ROOT:
                    cryptDocument((Document) o, node, crypt);
                    break;

                case LIST:
                    cryptList((List) o, node, crypt);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown class field to crypt for field " + node.fieldName + ": " + o.getClass());
            }
        } catch (ClassCastException e) {
            throw e;
        }
    }

    public static void cryptList(List list, Node node, Function<Object, Object> crypt) {
        if (node.type != Node.Type.LIST) throw new IllegalArgumentException("Expected list for " + node.fieldName + ", got " + node.type);

        Node mapChildren = node.children.get(0);
        for (int i = 0; i < list.size(); i++) {
            try {
                cryptFields(list.get(i), mapChildren, crypt);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public  static void cryptMap(Document document, Node node, Function<Object, Object> crypt) {
        Node mapChildren = node.children.get(0);
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            try {
                cryptFields(entry.getValue(), mapChildren, crypt);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public static void cryptDocument(Document document, Node node, Function<Object, Object> crypt) {
        for (Node childNode : node.children) {
            Object value = document.get(childNode.fieldName);
            if (value == null) continue;

            if (childNode.type == Node.Type.DIRECT) {
                try {
                    cryptDirect(document,childNode,crypt);
                    //document.put(childNode.fieldName, crypt.apply(value));
                } catch (Exception e) {
                    throw e;
                }
            } else {
                try {
                    cryptFields(value, childNode, crypt);
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public static void cryptDirect(Document document, Node childNode, Function<Object, Object> crypt){

        Object value=document.get(childNode.fieldName);
        Class<?> c=value.getClass();
        if(isPrimitiveStringWrapped(c)){
            try{
                document.put(childNode.fieldName, crypt.apply(value));
            } catch (Exception e) {
                throw e;
            }
        }
        else if(Collection.class.isAssignableFrom(c)){
            cryptDirectList((List)value,crypt);
            try {
                document.put(childNode.fieldName,value);
            }catch (Exception e) {
                throw e;
            }
        }
        else if(Map.class.isAssignableFrom(c)){
            cryptDirectMap((Document)value, crypt);
            try {
                document.put(childNode.fieldName,value);
            }catch (Exception e) {
                throw e;
            }
        }
        else{
            //assume now that it is a sub document
            cryptDirectDocument((Document)value,crypt);
            try {
                document.put(childNode.fieldName,value);
            }catch (Exception e) {
                throw e;
            }
        }
    }

    public static void cryptDirectList(List list, Function<Object, Object> crypt){

        if(!list.isEmpty()) {
            Object listValue = list.get(0);
            Class<?> lc=listValue.getClass();
            if(!(lc==null)) {
                if (isPrimitiveStringWrapped(lc)) {
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            if(list.get(i)!=null) {
                                Object encryptValue = crypt.apply(list.get(i));
                                list.set(i, encryptValue);
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                } else if (Collection.class.isAssignableFrom(lc)) {
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            //Object value=;
                            cryptDirectList((List) list.get(i), crypt);

                        } catch (Exception e) {
                            throw e;
                        }
                    }
                } else if (Map.class.isAssignableFrom(lc)) {
                    //deal with maps here
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            //Object value=;
                            cryptDirectMap((Document) list.get(i), crypt);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                } else {
                    //it will be a sub document
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            //Object value=;
                            cryptDirectDocument((Document) list.get(i), crypt);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
            }
        }
    }

    public static void cryptDirectMap(Document document, Function<Object, Object> crypt){
        Map map=(Map)document;
        if (!map.isEmpty()){
            Map.Entry<String,Object> entry = document.entrySet().iterator().next();
            Object value = entry.getValue();
            Class<?> c=value.getClass();
            if(!(c==null)) {
                if(isPrimitiveStringWrapped(c)){
                    for (Map.Entry<String, Object> mapEntry : document.entrySet()) {
                        try {
                            if(mapEntry.getValue()!=null) {
                                Object encrypted = crypt.apply(mapEntry.getValue());
                                document.put(mapEntry.getKey(), encrypted);
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
                else if (Collection.class.isAssignableFrom(c)){
                    for (Map.Entry<String, Object> mapEntry : document.entrySet()) {
                        try {
                            cryptDirectList((List) mapEntry.getValue(),crypt);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
                else if (Map.class.isAssignableFrom(c)) {
                    for (Map.Entry<String, Object> mapEntry : document.entrySet()) {
                        try {
                            cryptDirectMap((Document) mapEntry.getValue(),crypt);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
                else{
                    // deal with subdocuments here
                    for (Map.Entry<String, Object> mapEntry : document.entrySet()) {
                        try {
                            cryptDirectDocument((Document) mapEntry.getValue(),crypt);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }

            }
        }
    }

    public static void cryptDirectDocument(Document document, Function<Object, Object> crypt){

        Class<?> c=document.getClass();
        ReflectionUtils.doWithFields(c,field -> {
            if(Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || field.isAnnotationPresent(Id.class)) return;
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            if(isPrimitiveStringWrapped(fieldType)){
                Object encrypted= crypt.apply(field.get((Object) document));
                try {
                    document.put(field.getName(),encrypted);
                }catch (Exception e) {
                    throw e;
                }

            }
            else if (Collection.class.isAssignableFrom(fieldType)) {
                cryptDirectList((List)field.get((Object) document),crypt);

            } else if (Map.class.isAssignableFrom(fieldType)) {
                cryptDirectMap((Document) field.get((Object) document),crypt);
            } else {
                // descending into sub-documents
                cryptDirectDocument((Document) field.get((Object) document),crypt);
            }
        });
    }
    public static boolean isPrimitiveStringWrapped(Class c){
        if(c.isPrimitive()) return true;
        else{
            String s=c.toString();
            String type=s.split(" ")[1];
            if(type.startsWith("java.lang")) return true;
            else {
                return false;
            }
        }
    }
}
