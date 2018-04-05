package land.eies.poolmate.domain.support;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class JsonType implements UserType, ParameterizedType {

    public static final String CANONICAL = "land.eies.poolmate.domain.support.JsonType.canonical";

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModules(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private JavaType javaType;

    @Override
    public void setParameterValues(final Properties parameters) {
        javaType = mapper.getTypeFactory()
                .constructFromCanonical(parameters.getProperty(CANONICAL));
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    @Override
    public Class returnedClass() {
        return javaType.getRawClass();
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(final ResultSet resultSet,
                              final String[] names,
                              final SharedSessionContractImplementor session,
                              final Object owner) throws HibernateException, SQLException {
        try {
            return bytesToValue(resultSet.getBytes(names[0]));
        } catch (final IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void nullSafeSet(final PreparedStatement statement,
                            final Object value,
                            final int index,
                            final SharedSessionContractImplementor session) throws HibernateException, SQLException {
        try {
            statement.setObject(index, valueToString(value), Types.OTHER);
        } catch (final IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        if (value instanceof Serializable) {
            return (Serializable) deepCopy(value);
        }

        throw new HibernateException(
                new NotSerializableException(value.getClass().getName())
        );
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return deepCopy(original);
    }

    private Object bytesToValue(final byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }

        return mapper.readValue(bytes, javaType);
    }

    private String valueToString(final Object value) throws IOException {
        if (value == null) {
            return null;
        }

        return mapper.writeValueAsString(value);
    }
}
