import sun.security.provider.certpath.IndexedCollectionCertStore;

import java.util.Collection;
import java.util.Collections;

public interface IAccountFactory {
	public Collection<Account> getAccounts();
	
	public Account getAccount(int id);
}
