import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import crawler.SimpleCrawl;

public class Manager {

    private Map<Racer, List<String>> racerLinkMap;
    private Map<Racer, Integer> racerDistanceMap;

    private Map<String, List<String>> linkMap;

    private boolean solved = false;
    private Random r = new Random();
    private MainController controller;

    private final int maxDistance;
    private final String root;
    private final String target;

    public Manager(String root, String target, int maxDistance, MainController controller) {
        this.root = root;
        this.target = target;
        this.maxDistance = maxDistance;
        this.controller = controller;

        racerLinkMap = new HashMap<>();
        racerDistanceMap = new HashMap<>();
        linkMap = new HashMap<>();
        linkMap.put(root, new LinkedList<>());
    }

    public boolean isSolved() {
        return solved;
    }

    public String getTitle(String link) {
        return link.substring(link.lastIndexOf('/')).replace("_", " ").replace("/", "");
    }

    public synchronized void initRacer(Racer racer) {
        List<String> initList = new LinkedList<>();
        initList.add(root);
        racerLinkMap.put(racer, initList);
        racerDistanceMap.put(racer, 0);
    }

    public synchronized void request(Racer racer) throws IOException {
        if(solved) return;
        int currentDistance = racerDistanceMap.get(racer);
        
        if(currentDistance >= maxDistance) {
            initRacer(racer);
            return;
        }
        String currentLink = racerLinkMap.get(racer).get(0);
        if(linkMap.get(currentLink).size() == 0) {
            linkMap.put(currentLink, SimpleCrawl.crawlLinks(currentLink));
            
        }

        List<String> neighbors = linkMap.get(currentLink);

        if(neighbors.contains(target)) {
            System.out.println(racer.getName() + " solves");
            solved = true;
            List<String> solutionPath = racerLinkMap.get(racer);
            solutionPath.add(0, target);
            controller.setSolved(solutionPath);
            return;
        }

        boolean allExplored = false;
        int neighorCount = neighbors.size() - 1;
        int i = 0;

        for(String link : neighbors) {
            if(!linkMap.containsKey(link)) {
                linkMap.put(link, new LinkedList<>());
                List<String> path = racerLinkMap.get(racer);
                path.add(0, link);
                racerLinkMap.put(racer, path);
                break;
            }
            i++;
            if(i == neighorCount) allExplored = true;
        }

        if(allExplored) {
            List<String> path = racerLinkMap.get(racer);
            path.add(0, neighbors.get(r.nextInt(neighorCount) - 1));
            racerLinkMap.put(racer, path);
        }
    
        racerDistanceMap.put(racer, racerDistanceMap.get(racer) + 1);

        controller.setPane(Integer.parseInt(String.valueOf(racer.getName().charAt(racer.getName().length() - 1))), getTitle(currentLink));


    }
}
