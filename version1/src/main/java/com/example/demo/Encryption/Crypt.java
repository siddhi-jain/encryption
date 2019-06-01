package com.example.demo.Encryption;

import com.example.demo.reflection.Node;
import org.bson.Document;
import org.bson.types.Binary;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Crypt {
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
                    document.put(childNode.fieldName, crypt.apply(value));
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

}
