// package com.project.eventManagement.entity;

// import java.util.Date;

// import jakarta.annotation.Generated;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;

// @Entity
// @Table(name = "Entity")
// public class Category {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long categoryId;

//     private String name;

//     @Temporal(TemporalType.TIMESTAMP)
//     @Column(name = "created_at", nullable = false, updatable = false)
//     private Date createdAt;

//     @Temporal(TemporalType.TIMESTAMP)
//     @Column(name = "updated_at", nullable = false)
//     private Date updatedAt;

//     public Category() {

//     }

//     public Category(String name) {
//         this.name = name;
//         this.createdAt = new Date();
//         this.updatedAt = new Date();
//     }

//     public Long getCategoryId() {
//         return categoryId;
//     }

//     public void setCategoryId(Long categoryId) {
//         this.categoryId = categoryId;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public Date getCreatedAt() {
//         return createdAt;
//     }

//     public void setCreatedAt(Date createdAt) {
//         this.createdAt = createdAt;
//     }

//     public Date getUpdatedAt() {
//         return updatedAt;
//     }

//     public void setUpdatedAt(Date updatedAt) {
//         this.updatedAt = updatedAt;
//     }

// }
