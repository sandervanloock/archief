package be.chiroelzestraat.controllers;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public final ModelAndView viewAndFlushCache(
            @RequestParam(value = "flush", required = false) boolean flushAllCache,
            @RequestParam(value = "clearCacheName", defaultValue = "", required = false) String clearCacheName) {
        ModelAndView mav = new ModelAndView("debug/viewCache");

        List<Cache> caches = new ArrayList<Cache>();

        if (cacheManager != null) {

            String[] cacheNames = cacheManager.getCacheNames();

            for (String cacheName : cacheNames) {
                Cache c = cacheManager.getCache(cacheName);
                if (flushAllCache || cacheName.equals(clearCacheName)) {
                    c.removeAll();
                }
                c.getSize();
                caches.add(c);
            }
        }

        mav.addObject("flushed", flushAllCache);
        mav.addObject("caches", caches);

        return mav;
    }
}
