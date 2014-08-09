/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */


package truelauncher.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

public class EventBus {

	private static Object lock = new Object();

	private static HashMap<String, LinkedList<MethodInfo>> registeredListeners = new HashMap<String, LinkedList<MethodInfo>>();

	public static void registerListener(Object instance) {
		synchronized (lock) {
			for (Method method : instance.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(EventHandler.class)) {
					if ((method.getParameters().length == 1) && Event.class.isAssignableFrom(method.getParameters()[0].getType())) {
						registerListener(instance, method.getParameters()[0].getType().getName(), method);
					}
				}
			}
		}
	}

	private static void registerListener(Object instance, String name, Method method) {
		if (!registeredListeners.containsKey(name)) {
			registeredListeners.put(name, new LinkedList<MethodInfo>());
		}
		registeredListeners.get(name).add(new MethodInfo(method, instance));
	}

	public static void unregisterListener(Object instance) {
		synchronized (lock) {
			HashSet<Method> methods = new HashSet<Method>(Arrays.asList(instance.getClass().getDeclaredMethods()));
			Iterator<Entry<String, LinkedList<MethodInfo>>> lit = registeredListeners.entrySet().iterator();
			while (lit.hasNext()) {
				Entry<String, LinkedList<MethodInfo>> entry = lit.next();
				Iterator<MethodInfo> valueit = entry.getValue().iterator();
				while (valueit.hasNext()) {
					MethodInfo methodinfo = valueit.next();
					if (methods.contains(methodinfo.getMethod())) {
						valueit.remove();
					}
				}
				if (entry.getValue().size() == 0) {
					lit.remove();
				}
			}
		}
	}

	public static void callEvent(Event event) {
		synchronized (lock) {
			if (!registeredListeners.containsKey(event.getClass().getName())) {
				return;
			}
			for (MethodInfo methodinfo : registeredListeners.get(event.getClass().getName())) {
				try {
					methodinfo.getMethod().setAccessible(true);
					methodinfo.getMethod().invoke(methodinfo.getObject(), event);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface EventHandler{
	}

	private static class MethodInfo {

		private Method method;
		private Object instance;

		public MethodInfo(Method method, Object instance) {
			this.method = method;
			this.instance = instance;
		}

		public Method getMethod() {
			return method;
		}

		public Object getObject() {
			return instance;
		}

	}

}
