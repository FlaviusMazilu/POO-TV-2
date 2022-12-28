package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;

public abstract class IO {
    public static ObjectMapper objectMapper;
    public static Input inputData;
    public static ArrayNode output;
}
