package com.cs411databases.databasefinalproject.objects;

public interface DatabaseObject {
    int getID();
    String getIDColumnName();
    String getAttributeName(int index);
}
