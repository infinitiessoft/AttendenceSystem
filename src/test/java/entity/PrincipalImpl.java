package entity;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PrincipalImpl implements Principal {

	public static class UserDetail {

		private Long id;
		private String username;
		private String password;
		private Date dateofjoined;
		private Set<Authority> authorities = new HashSet<Authority>(0);
		private Boolean accountNonExpired;
		private Boolean accountNonLocked;
		private Boolean credentialsNonExpired;
		private Boolean enabled;

		public UserDetail() {

		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setAuthorities(Set<Authority> authorities) {
			this.authorities = authorities;
		}

		public Set<Authority> getAuthorities() {
			return authorities;
		}

		public String getPassword() {
			return password;
		}

		public String getUsername() {
			return username;
		}

		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}

		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}

		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		public boolean isEnabled() {
			return enabled;
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

		public Boolean getAccountNonExpired() {
			return accountNonExpired;
		}

		public void setAccountNonExpired(Boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
		}

		public Boolean getAccountNonLocked() {
			return accountNonLocked;
		}

		public void setAccountNonLocked(Boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
		}

		public Boolean getCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

		@Override
		public String toString() {
			return "EmployeeUserDetail [id=" + id + ", username=" + username
					+ ", password=" + password + ", authorities=" + authorities
					+ "]";
		}
	}

	public static class Credentials {
		private Long id;
		private String name;
		private Date dateofjoined;
		private Set<Authority> authorities = new HashSet<Authority>(0);

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getDateofjoined() {
			return dateofjoined;
		}

		public void setDateofjoined(Date dateofjoined) {
			this.dateofjoined = dateofjoined;
		}

		public Set<Authority> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(Set<Authority> authorities) {
			this.authorities = authorities;
		}

	}

	public static class Authority {
		private String authority;

		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}

	}

	private String details;
	private String name;
	private Set<Authority> authorities = new HashSet<Authority>(0);
	private Boolean authenticated;
	private UserDetail principal;
	private Credentials credentials;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public UserDetail getPrincipal() {
		return principal;
	}

	public void setPrincipal(UserDetail principal) {
		this.principal = principal;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
