package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SDTProfiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SimpleProfiler implements SDTProfiler {

    private final Map<String, Subject> subjects = new TreeMap<>();
    private final ArrayList<Item> result = new ArrayList<>();
    private boolean hasOld = false;
    private long totalMilliseconds = 0;
    private long totalTicks = 0;

    @Override
    public void start(String name) {
        start(name, true);
    }

    @Override
    public void start(String name, boolean keep) {
        Subject subject = subjects.computeIfAbsent(name, (key) -> new Subject(name, keep));
        subject.keep = keep;
        subject.reset();
    }

    @Override
    public void end(String name) {
        Subject subject = subjects.get(name);

        if (subject == null) {
            return;
        }

        subject.end();
    }

    @Override
    public void build() {
        if (subjects.isEmpty()) {
            this.result.clear();
            return;
        }

        long duration = 0;
        for (Subject subject : subjects.values()) {
            if (!subject.updated)
                continue;

            duration += (subject.end - subject.start);
        }

        // TODO handle it in a way to support unfinished subjects!
        boolean notReady = (duration == 0);
        if (!hasOld && notReady) {
            this.result.clear();
            return;
        }

        this.totalTicks = 0;
        this.totalMilliseconds = 0;

        int index = 0;
        boolean allocated;
        for (Subject subject : subjects.values()) {
            MutableItem item;

            // Use the already allocated mutable objects or allocate new ones if not enough
            if (index >= this.result.size()) {
                item = new MutableItem();
                allocated = false;
            } else {
                item = (MutableItem) this.result.get(index);
                allocated = true;
            }
            index++;

            item.name = subject.name;
            item.updated = subject.updated;
            long subjectDuration = (subject.end - subject.start);
            item.ticks = subjectDuration / 50_000_000L;
            item.milliseconds = subjectDuration / 1_000_000L;

            if (!subject.updated) {
                item.percent = 0;
                if (!allocated)
                    this.result.add(item);
                continue;
            }

            totalTicks += item.ticks;
            totalMilliseconds += item.milliseconds;

            if (notReady) {
                continue;
            }

            item.percent = (((double) subjectDuration / (double) duration) * 100.0);
            if (!allocated)
                this.result.add(item);
        }

        // Remove the unused allocated mutable objects
        while ((index + 1) < this.result.size()) {
            this.result.remove(index + 1);
        }

        reset();
    }

    @Override
    public Collection<Item> getItems() {
        return result;
    }

    @Override
    public long getTotalTicks() {
        return totalTicks;
    }

    @Override
    public long getTotalMilliseconds() {
        return totalMilliseconds;
    }

    private void reset() {
        List<String> subjectsToRemove = new ArrayList<>();

        hasOld = false;
        for (Subject subject : subjects.values()) {
            if (!subject.keep) {
                subjectsToRemove.add(subject.name);
                continue;
            }

            hasOld = true;
            subject.updated = false;
        }

        for (String subject : subjectsToRemove) {
            subjects.remove(subject);
        }
    }

    private final static class MutableItem implements SDTProfiler.Item {
        private String name;
        private boolean updated;
        private long ticks;
        private long milliseconds;
        private double percent;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isUpdated() {
            return updated;
        }

        @Override
        public long getTicks() {
            return ticks;
        }

        @Override
        public long getMilliseconds() {
            return milliseconds;
        }

        @Override
        public double getPercent() {
            return percent;
        }
    }

    private final static class Subject {
        private final String name;
        private boolean keep;
        private boolean updated = false;
        private long start;
        private long end = -1L;

        public Subject(String name, boolean keep) {
            this.name = name;
            this.keep = keep;
            reset();
        }

        public void end() {
            this.end = System.nanoTime();
            updated = true;
        }

        public void reset() {
            this.start = System.nanoTime();
        }
    }
}
