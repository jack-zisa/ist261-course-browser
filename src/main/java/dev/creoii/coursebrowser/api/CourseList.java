package dev.creoii.coursebrowser.api;

import dev.creoii.coursebrowser.backend.course.Course;

import java.util.*;

public class CourseList implements Collection<Course> {
    private final List<Course> courses;

    public CourseList() {
        this.courses = new ArrayList<>();
    }

    public Course getFirst() {
        return courses.getFirst();
    }

    @Override
    public int size() {
        return courses.size();
    }

    @Override
    public boolean isEmpty() {
        return courses.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return courses.contains(o);
    }

    @Override
    public Iterator<Course> iterator() {
        return courses.iterator();
    }

    @Override
    public Object[] toArray() {
        return new CourseList[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return courses.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return courses.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(courses).containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return courses.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return courses.retainAll(c);
    }

    @Override
    public void clear() {
        courses.clear();
    }

    @Override
    public boolean addAll(Collection<? extends Course> c) {
        return courses.addAll(c);
    }

    @Override
    public boolean add(Course course) {
        return courses.add(course);
    }
}
