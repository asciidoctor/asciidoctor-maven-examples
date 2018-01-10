package example;

public class StringUtils {
    // tag::contains[]
    public boolean contains(String haystack, String needle) {
        return haystack.contains(needle); //<1>
    }
    // end::contains[]
}
