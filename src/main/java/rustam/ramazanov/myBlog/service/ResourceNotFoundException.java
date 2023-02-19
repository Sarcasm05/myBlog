package rustam.ramazanov.myBlog.service;



public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String post, String id, Long id1) {
    }

    public ResourceNotFoundException(String post) {
    }
}
