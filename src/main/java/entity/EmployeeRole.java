
// package entity;
//
// import java.io.Serializable;
//
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;
//
// @Entity
// @Table(name = "employee_role")
// public class EmployeeRole implements Serializable {
// private static final long serialVersionUID = 7711505597348200997L;
//
// @Id
// @Column(name = "employee_id")
// @GeneratedValue(strategy = GenerationType.AUTO)
// private Long employee_id;
//
// @Column(name = "role_id", nullable = false)
// private Long role_id;
//
// @ManyToOne
// @JoinColumn(name = "role")
// private RoleEntity role;
//
// public EmployeeRole(Long employee_id, Long role_id, RoleEntity role) {
// super();
// this.employee_id = employee_id;
// this.role_id = role_id;
// this.role = role;
// }
//
// public EmployeeRole() {
// super();
//
// }
//
// }
