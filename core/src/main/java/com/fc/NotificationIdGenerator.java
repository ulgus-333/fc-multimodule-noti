package com.fc;

import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;

@UtilityClass
public class NotificationIdGenerator {

    public static String generateId() {
        return new ObjectId().toString();
    }
}
