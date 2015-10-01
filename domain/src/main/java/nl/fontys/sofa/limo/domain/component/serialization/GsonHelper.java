/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.MasterData;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.IntegerInputValue;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;

/**
 *
 * @author Convict42
 */
public class GsonHelper {
    private static Gson gson;
    
    private GsonHelper() {
    }
    
    public static Gson getInstance(){
        if(gson == null)
        {
            GsonBuilder gsonB = new GsonBuilder();
            //Serializer
            gsonB.registerTypeAdapter(Value.class, new ValueSerializer());
            gsonB.registerTypeAdapter(MultiModeLeg.class, new MultiModeLegSerializer());
            gsonB.registerTypeAdapter(ScheduledLeg.class, new ScheduleLegSerializer());
            gsonB.registerTypeAdapter(Distribution.class, new DistributionSerializer());
            gsonB.registerTypeAdapter(DoubleInputValue.class, new InputValueSerializer());
            gsonB.registerTypeAdapter(IntegerInputValue.class, new InputValueSerializer());
            
            //Deserializer
            gsonB.registerTypeAdapter(Value.class, new ValueDeserializer());
            gsonB.registerTypeAdapter(Leg.class, new LegDeserializer());
            gsonB.registerTypeAdapter(Icon.class, new IconDeserializer());
            gsonB.registerTypeAdapter(Distribution.class, new DistributionDeserializer());
            gsonB.registerTypeAdapter(InputValue.class, new InputValueDeserializer());
            gsonB.registerTypeAdapter(MasterData.class, new MasterDataDeserializer());
            
            gson = gsonB.excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        }   
        return gson;
    }
}
