package com.cms.dto;

public class UserStats {
    private long totalUsers;
    private long activeUsers;
    private long adminUsers;
    private long todayRegistrations;

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getActiveUsers() { return activeUsers; }
    public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }

    public long getAdminUsers() { return adminUsers; }
    public void setAdminUsers(long adminUsers) { this.adminUsers = adminUsers; }

    public long getTodayRegistrations() { return todayRegistrations; }
    public void setTodayRegistrations(long todayRegistrations) { this.todayRegistrations = todayRegistrations; }
}