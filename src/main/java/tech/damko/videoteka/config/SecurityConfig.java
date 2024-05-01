package tech.damko.videoteka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("password")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().
                disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/users/save");
        web.ignoring().antMatchers("/users/update");
        web.ignoring().antMatchers("/users/login");
        web.ignoring().antMatchers("/movie/all");
        web.ignoring().antMatchers("/movie/add");
        web.ignoring().antMatchers("/movie/update");
        web.ignoring().antMatchers("/movie/load");
        web.ignoring().antMatchers("/movie/mobileLoad/{disc}");
        web.ignoring().antMatchers("/movie/delete/{id}");
        web.ignoring().antMatchers("/movie/deleteMovieByDisc/{disc}");
        web.ignoring().antMatchers("/file/upload");
        web.ignoring().antMatchers("/file/export");
        web.ignoring().antMatchers("/file/files");
        web.ignoring().antMatchers("/file/downloadfiles");
        web.ignoring().antMatchers("/file/delete/{filename:.+}");
        web.ignoring().antMatchers("/file/filesForLogin/{username:.+}");
        web.ignoring().antMatchers("/file/loadMoviesFromXml/{filename:.+}");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
