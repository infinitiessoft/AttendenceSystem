package security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import entity.Employee;
import entity.EmployeeRole;

public class EmployeeUserDetail implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private Date dateofjoined;
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(0);

	public EmployeeUserDetail() {

	}

	public EmployeeUserDetail(Employee user) {
		username = user.getUsername();
		password = user.getPassword();
		id = user.getId();
		dateofjoined = user.getDateofjoined();
		Set<EmployeeRole> roles = user.getEmployeeRoles();
		for (EmployeeRole role : roles) {
			authorities
					.add(new SimpleGrantedAuthority(role.getRole().getName()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	@XmlTransient
	public String getPassword() {
		return password;
	}

	@Override
	@XmlTransient
	public String getUsername() {
		return username;
	}

	@Override
	@XmlTransient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@XmlTransient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@XmlTransient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@XmlTransient
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date getDateofjoined() {
		return dateofjoined;
	}

	public void setDateofjoined(Date dateofjoined) {
		this.dateofjoined = dateofjoined;
	}

	@Override
	public String toString() {
		return "EmployeeUserDetail [id=" + id + ", username=" + username
				+ ", password=" + password + ", authorities=" + authorities
				+ "]";
	}

}
