
package ims.departments;

public class Department {
    
    private String department_name;
    private String department_code;
    private String location;
    private Integer number_of_items;

    public Department(String department_name, String department_code, String location, Integer number_of_items) {
        this.department_name = department_name;
        this.department_code = department_code;
        this.location = location;
        this.number_of_items = number_of_items;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumber_of_items() {
        return number_of_items;
    }

    public void setNumber_of_items(Integer number_of_items) {
        this.number_of_items = number_of_items;
    }
    
    
    
    
    
}
