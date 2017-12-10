package land.eies.poolmate.support;

public interface Deletable {

    void delete();

    void recover();

    boolean isDeleted();
}
