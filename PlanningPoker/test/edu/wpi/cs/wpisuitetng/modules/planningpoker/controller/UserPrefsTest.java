package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;

/**
 * Tests the UserPrefsController
 * 
 * @author Sam Carlberg
 * 
 */
public class UserPrefsTest {

	UserPrefsController controller;
	MockNetwork network;
	MockData db;
	User u; // logged in user
	User o1, o2, o3, o4; // other users
	Project testProject;
	String mockSsid;
	Session defaultSession;

	@Before
	public void setup() {
		controller = new UserPrefsController();
		network = new MockNetwork();
		u = new User("Local User", "localuser", "1234", 0);
		o1 = new User("Remote User 1", "remoteuser1", "1234", 1);
		o2 = new User("Remote User 2", "remoteuser2", "1234", 2);
		o3 = new User("Remote User 3", "remoteuser3", "1234", 3);
		o4 = new User("Remote User 4", "remoteuser4", "1234", 4);

		testProject = new Project("test", "1");
		mockSsid = "abc123";
		defaultSession = new Session(o1, testProject, mockSsid);

		network = new MockNetwork();
		db = new MockData(new HashSet<Object>());
		db.save(o1, testProject);
		db.save(o2, testProject);
		db.save(u, testProject);
		db.save(o3, testProject);
		db.save(o4, testProject);
	}

	@Test
	public void testGetUser() {
		User u = controller.getUser();
		assertNotNull(u);
		assertTrue(this.u.equals(u));
	}

}
