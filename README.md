# framework-demo-actuator
## actuator 설정방법
1. pom.xml 파일에 spring-boot-starter-actuator 디펜던시를 추가한다.
2. application.yml 파일에 actuator endpoints/endpoint 설정을 추가한다.
```
management:
  endpoints:
    web:
      base-path: /x2commerce-system
      exposure:
        include:
          - "info"
          - "health"
          - "httptrace"
          - "metrics"
  endpoint:
    health:
      show-details: always
      probes:
        enabled: true
```
3. 노출된 endpoint들에 대해 spring security 권한 설정을 적용한다.
   필수는 아니더라도 acuator 조회되는 정보가 보안상 예민할 수 있으므로 적용하는게 좋다.
```
@Override
protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable()
      .authorizeRequests()
      .antMatchers("/", "/home").permitAll()
      // actuator 설정. kube probes readines/liveness 체크 권한허용
      .antMatchers("/actuator/health/readiness").permitAll() 
      .antMatchers("/actuator/health/liveness").permitAll()
      // actuator 설정. SUPERUSER 권한허용
      //.antMatchers("/actuator/**").hasRole("SUPERUSER")
      .anyRequest().authenticated()
      .and()
      ...
```
4. kubernetes 사용한다면 management.endpoint.health.probes.enabled = true 필수로 설정해야한다. 
   설정후 http://hostname/actuator/health/liveness, http://hostname/actuator/health/readiness 조회가 가능해진다.
