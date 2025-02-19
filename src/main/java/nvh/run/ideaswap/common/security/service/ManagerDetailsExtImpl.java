package nvh.run.ideaswap.common.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Managers;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerDetailsExtImpl extends Managers implements Serializable, UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 1L;
    Collection<? extends GrantedAuthority> authorities;

    public ManagerDetailsExtImpl(Managers manager, Collection<? extends GrantedAuthority> authorities) {
        // Gọi constructor của lớp cha để sao chép các thuộc tính từ user
        super(manager);
//        BeanUtils.copyProperties(user, this); //tương tự super(user.getAttributes...)
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerDetailsExtImpl manager = (ManagerDetailsExtImpl) o;
        return Objects.equals(getId(), manager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public void eraseCredentials() {
        super.setPassword(null);
    }
}
