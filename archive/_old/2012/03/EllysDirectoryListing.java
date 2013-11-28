package tmp;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class EllysDirectoryListing {
    public String[] getFiles(String[] files) {
        if (finish(files)) return files;
        for (int i = 0; i < files.length; i++) {
            if (isEnd(files[i])) {
                swap(files, i, files.length - 1);
                break;
            }
        }
        if (finish(files)) return files;
        for (int i = 0; i < files.length; i++) {
            if (isEnd(files[i])) {
                swap(files, i, files.length - 2);
                break;
            }
        }
        return files;
    }

    private boolean isEnd(String file) {
        return file.equals("") || file.equals(".");
    }

    private void swap(String[] files, int i, int j) {
        String tmp = files[i]; files[i] = files[j]; files[j] = tmp;
    }

    private boolean finish(String[] files) {
        return isEnd(files[files.length - 1]) && isEnd(files[files.length - 2]);
    }


}

