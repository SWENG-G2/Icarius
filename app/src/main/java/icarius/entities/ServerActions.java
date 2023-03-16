package icarius.entities;

import icarius.auth.User;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;

public interface ServerActions {
    public Long create(User user, PostRequest request);
    public String read(GetRequest request);
    public void update(User user, PatchRequest request);
    public Boolean delete(User user, DeleteRequest request);
}
