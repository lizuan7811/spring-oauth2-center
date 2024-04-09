//package oath2resourceserver.properties;
//
//import org.springframework.jdbc.core.SqlParameterValue;
//import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//import java.sql.Types;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//
//@Component
//public class SqlOAuth2AuthorizedClientParametersMapper extends JdbcOAuth2AuthorizedClientService.OAuth2AuthorizedClientParametersMapper{
//
//    /**
//    * @Description: 驗證取得token使用
//    * @date: 2024/4/9
//    * @time: 17:18
//    **/
//    @Override
//    public List<SqlParameterValue> apply(JdbcOAuth2AuthorizedClientService.OAuth2AuthorizedClientHolder holder) {
//        return super.apply(holder).stream()
//                .map(parameter -> {
//                    if (parameter.getSqlType() == Types.BLOB) {
//                        return new SqlParameterValue(Types.BINARY, parameter.getValue());
//                    } else if (parameter.getSqlType() == Types.TIMESTAMP) {
//                        // saving as UTC stamp!
//                        Object value = parameter.getValue();
//                        Timestamp timestampValue = (Timestamp) value;
//                        if (timestampValue != null) {
//                            Instant instant = timestampValue.toInstant();
//                            LocalDateTime dateTimeUtc = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
//                            Timestamp timestampUtc = Timestamp.valueOf(dateTimeUtc);
//                            return new SqlParameterValue(Types.TIMESTAMP, timestampUtc);
//                        }
//                    }
//                    return parameter;
//                })
//                .collect(Collectors.toList());
//    }
//}