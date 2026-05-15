package com.lostandfound.model;

/**

- ItemModel - Represents a lost or found item
- Package: com.lostandfound.model
  */
  public class ItemModel {
  
  private int    id;
  private int    userId;
  private String type;          // “lost” or “found”
  private String category;
  private String description;
  private String color;
  private String brand;
  private String dateOccurred;
  private String location;
  private String imagePath;
  private String status;        // “pending”, “claimed”, “resolved”
  private String createdAt;
  
  // Optional: joined field from users table
  private String reportedBy;
  
  public ItemModel() {}
  
  // Getters
  public int    getId()           { return id; }
  public int    getUserId()       { return userId; }
  public String getType()         { return type; }
  public String getCategory()     { return category; }
  public String getDescription()  { return description; }
  public String getColor()        { return color; }
  public String getBrand()        { return brand; }
  public String getDateOccurred() { return dateOccurred; }
  public String getLocation()     { return location; }
  public String getImagePath()    { return imagePath; }
  public String getStatus()       { return status; }
  public String getCreatedAt()    { return createdAt; }
  public String getReportedBy()   { return reportedBy; }
  
  // Setters
  public void setId(int id)                     { this.id = id; }
  public void setUserId(int userId)             { this.userId = userId; }
  public void setType(String type)              { this.type = type; }
  public void setCategory(String category)      { this.category = category; }
  public void setDescription(String description){ this.description = description; }
  public void setColor(String color)            { this.color = color; }
  public void setBrand(String brand)            { this.brand = brand; }
  public void setDateOccurred(String date)      { this.dateOccurred = date; }
  public void setLocation(String location)      { this.location = location; }
  public void setImagePath(String imagePath)    { this.imagePath = imagePath; }
  public void setStatus(String status)          { this.status = status; }
  public void setCreatedAt(String createdAt)    { this.createdAt = createdAt; }
  public void setReportedBy(String reportedBy)  { this.reportedBy = reportedBy; }
  }
