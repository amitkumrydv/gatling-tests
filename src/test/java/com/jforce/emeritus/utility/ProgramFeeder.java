package com.jforce.emeritus.utility;

import io.gatling.javaapi.core.FeederBuilder;

import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

/**
 * ProgramFeeder generates unique program details for each virtual user.
 */
public class ProgramFeeder {
	
	private static final Random random = new Random();

    public static FeederBuilder<Object> getProgramData() {
        // Create a list of unique program entries (for example, 500)
        List<Map<String, Object>> feederData = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("programCode", "abcdw" + UUID.randomUUID().toString().substring(0, 8));
            data.put("programName", "testprogram" + getRandomAlphabeticString(4));
            data.put("modeOfLearning", "Online");
            data.put("specializationType", "-1");
            data.put("userId", "MDM-ADMIN"); // Added as it's used in scenario
            feederData.add(data);
        }

        for(Map<String, Object> record : feederData) {
        	
        	System.out.println("record -- "+record);
        }
        
        // Convert List to Map[]
        @SuppressWarnings("unchecked")
        Map<String, Object>[] feederArray = feederData.toArray(new Map[0]);

        return io.gatling.javaapi.core.CoreDsl.arrayFeeder(feederArray);
    }
    
    
    
    public static String getRandomAlphabeticString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomAlphabeticChar());
        }
        return sb.toString();
    }
    
    
    public static char getRandomAlphabeticChar() {
        while (true) {
            int ascii = 65 + random.nextInt(58); // 65 to 122 inclusive
            char ch = (char) ascii;
            if (Character.isLetter(ch)) {
                return ch;
            }
        }
    }
    
    
}
