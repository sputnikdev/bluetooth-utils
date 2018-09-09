import java.util.regex.Matcher
import java.util.regex.Pattern

class OUIUpdater {

    public static update(project) {
        java.net.URL url = new java.net.URL("http://standards-oui.ieee.org/oui.txt");
        Pattern id = Pattern.compile('^(\\w{6}).*$');
        Scanner s = new Scanner(url.openStream()).useDelimiter('\r\n');
        s.next(); //
        s.next(); // skip header

        HashSet<Integer> registry = new HashSet<>();

        while (s.hasNext()) {
            String line = s.next();
            Matcher matcher = id.matcher(line);
            if (matcher.find()) {
                String oui = matcher.group(1);
                registry.add(Integer.valueOf(oui, 16));
            }
        }

        File regFile = new File("src/main/resources/oui_registry.ser");
        if (regFile.exists()) {
            regFile.delete();
        }
        regFile.createNewFile();
        FileOutputStream fileOut = new FileOutputStream(regFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

        objectOut.writeObject(registry);

        objectOut.close();

        println "Organizational Unique Identifiers registry has been updated: " + registry.size();
    }
}