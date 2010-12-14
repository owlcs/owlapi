package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.net.URI;

public class URIResolveTester {
//	private static URI resolve(URI base, URI child) {
//		// check if child if opaque first so that NPE is thrown
//		// if child is null.
//		if (child.isOpaque() || base.isOpaque())
//			return child;
//		// 5.2 (2): Reference to current document (lone fragment)
//		if ((child.getScheme() == null) && (child.getAuthority() == null)
//				&& child.getPath().equals("") && (child.getFragment() != null)
//				&& (child.getQuery() == null)) {
//			if ((base.getFragment() != null)
//					&& child.getFragment().equals(base.getFragment())) {
//				return base;
//			}
//			URI ru = new URI();
//			ru.scheme = base.scheme;
//			ru.authority = base.authority;
//			ru.userInfo = base.userInfo;
//			ru.host = base.host;
//			ru.port = base.port;
//			ru.path = base.path;
//			ru.fragment = child.fragment;
//			ru.query = base.query;
//			return ru;
//		}
//		// 5.2 (3): Child is absolute
//		if (child.getScheme() != null)
//			return child;
//		URI ru = new URI(); // Resolved URI
//		ru.scheme = base.scheme;
//		ru.query = child.query;
//		ru.fragment = child.fragment;
//		// 5.2 (4): Authority
//		if (child.authority == null) {
//			ru.authority = base.authority;
//			ru.host = base.host;
//			ru.userInfo = base.userInfo;
//			ru.port = base.port;
//			String cp = (child.getPath() == null) ? "" : child.getPath();
//			if ((cp.length() > 0) && (cp.charAt(0) == '/')) {
//				// 5.2 (5): Child path is absolute
//				ru.path = child.path;
//			} else {
//				// 5.2 (6): Resolve relative path
//				ru.path = resolvePath(base.path, cp, base.isAbsolute());
//			}
//		} else {
//			ru.authority = child.authority;
//			ru.host = child.host;
//			ru.userInfo = child.userInfo;
//			ru.host = child.host;
//			ru.port = child.port;
//			ru.path = child.path;
//		}
//		// 5.2 (7): Recombine (nothing to do here)
//		return ru;
//	}

	public static void resolve(String s) {
		URI uri = URI.create(s);
		if (uri.isAbsolute()||uri.isOpaque()) {
			uri.toString();
		} else {
			toURI().resolve(uri);
		}
	}

	public static URI toURI() {
		return URI.create("urn:test:testuri#testuri");
	}

	public static void resolveOld(String s) {
		toURI().resolve(s);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 1000; j++) {
				String s = "urn:anothertest:testuri#anothertesturi" + j;
				resolve(s);
				resolveOld(s);
			}
			for (int j = 0; j < 1000; j++) {
				String s = "#anothertesturi" + j;
				resolve(s);
				resolveOld(s);
			}
		}
	}
}
