package oauth2ResourcesServer.websecurity.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import oauth2ResourcesServer.websecurity.entity.Role;

/**
 * 使用者Details
 */
public class UserDet implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "UserDet [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", roles=" + roles + "]";
	}

	private Integer id;
	private String username;
	private String password;
	private Boolean enabled;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private List<Role> roles=new ArrayList<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<SimpleGrantedAuthority> authorities=new HashSet<>();
		roles.forEach(role->{
			SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(role.getName());
			authorities.add(simpleGrantedAuthority);
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Integer getId() {
		return id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
	
}
