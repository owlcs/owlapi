package org;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.AutoIRIMapper;

public class LoadSweetTestCase extends TestBase {

    @Test
    public void should() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            IRI.create(new File(RESOURCES, "importscyclic/relaMath.owl")));
        o.getImportsClosure().stream().forEach(x -> {
            String s = x.getAxioms().stream().filter(ax -> ax.toString().contains("Error1"))
                .map(Object::toString).collect(Collectors.joining("\n"));
            if (!s.isEmpty()) {
                System.out.println("LoadSweet.main() " + x.getOntologyID() + "\n" + s);
            }
        });
        findMultipleDeclarations(o);
        findPunnings(o);
    }

    public static void main(String[] args) throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getIRIMappers().add(mapper());
        OWLOntology o = m.loadOntology(IRI.create("http://sweetontology.net/relaMath"));
        o.getImportsClosure().stream().forEach(x -> {
            String s = x.getAxioms().stream().filter(ax -> ax.toString().contains("Error1"))
                .map(Object::toString).collect(Collectors.joining("\n"));
            if (!s.isEmpty()) {
                System.out.println("LoadSweet.main() " + x.getOntologyID() + "\n" + s);
            }
        });
        findMultipleDeclarations(o);
        findPunnings(o);
    }

    protected static void findMultipleDeclarations(OWLOntology o) {
        Map<IRI, List<OWLAxiom>> map = new HashMap<>();
        o.getAxioms(true).stream().filter(ax -> ax instanceof OWLDeclarationAxiom)
            .map(ax -> (OWLDeclarationAxiom) ax).forEach(
                ax -> map.computeIfAbsent(ax.getEntity().getIRI(), x -> new ArrayList<>()).add(ax));
        map.forEach((a, b) -> {
            if (b.size() > 1) {
                System.out.println("Declared multiple times with different type " + a);
            }
        });
    }

    protected static void findPunnings(OWLOntology o) {
        Map<IRI, List<OWLEntity>> map = new HashMap<>();
        o.getSignature(Imports.INCLUDED).stream()
            .forEach(ax -> map.computeIfAbsent(ax.getIRI(), x -> new ArrayList<>()).add(ax));
        map.forEach((a, b) -> {
            if (b.size() > 1) {
                System.out.println("Punned with different types " + a + " " + b.stream()
                    .map(x -> x.getEntityType().toString()).collect(Collectors.joining(", ")));
            }
        });
    }

    protected static OWLOntologyIRIMapper mapper() {
        Map<IRI, IRI> map = new HashMap<>();
        String sweet = "file:///Users/ignazio/workspace/sweet/src/";
        map.put(IRI.create("http://sweetontology.net/human"), IRI.create(sweet + "human.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanAgriculture"),
            IRI.create(sweet + "humanAgriculture.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanCommerce"),
            IRI.create(sweet + "humanCommerce.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanDecision"),
            IRI.create(sweet + "humanDecision.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanEnvirAssessment"),
            IRI.create(sweet + "humanEnvirAssessment.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanEnvirConservation"),
            IRI.create(sweet + "humanEnvirConservation.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanEnvirControl"),
            IRI.create(sweet + "humanEnvirControl.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanEnvirStandards"),
            IRI.create(sweet + "humanEnvirStandards.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanJurisdiction"),
            IRI.create(sweet + "humanJurisdiction.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanKnowledgeDomain"),
            IRI.create(sweet + "humanKnowledgeDomain.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanResearch"),
            IRI.create(sweet + "humanResearch.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanTechReadiness"),
            IRI.create(sweet + "humanTechReadiness.ttl"));
        map.put(IRI.create("http://sweetontology.net/humanTransportation"),
            IRI.create(sweet + "humanTransportation.ttl"));
        map.put(IRI.create("http://sweetontology.net/matr"), IRI.create(sweet + "matr.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrAerosol"),
            IRI.create(sweet + "matrAerosol.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrAnimal"),
            IRI.create(sweet + "matrAnimal.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrBiomass"),
            IRI.create(sweet + "matrBiomass.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrCompound"),
            IRI.create(sweet + "matrCompound.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrElement"),
            IRI.create(sweet + "matrElement.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrElementalMolecule"),
            IRI.create(sweet + "matrElementalMolecule.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrEnergy"),
            IRI.create(sweet + "matrEnergy.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrEquipment"),
            IRI.create(sweet + "matrEquipment.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrFacility"),
            IRI.create(sweet + "matrFacility.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrIndustrial"),
            IRI.create(sweet + "matrIndustrial.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrInstrument"),
            IRI.create(sweet + "matrInstrument.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrIon"), IRI.create(sweet + "matrIon.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrIsotope"),
            IRI.create(sweet + "matrIsotope.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrMicrobiota"),
            IRI.create(sweet + "matrMicrobiota.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrMineral"),
            IRI.create(sweet + "matrMineral.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrNaturalResource"),
            IRI.create(sweet + "matrNaturalResource.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrOrganicCompound"),
            IRI.create(sweet + "matrOrganicCompound.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrParticle"),
            IRI.create(sweet + "matrParticle.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrPlant"),
            IRI.create(sweet + "matrPlant.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrRock"),
            IRI.create(sweet + "matrRock.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrRockIgneous"),
            IRI.create(sweet + "matrRockIgneous.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrSediment"),
            IRI.create(sweet + "matrSediment.ttl"));
        map.put(IRI.create("http://sweetontology.net/matrWater"),
            IRI.create(sweet + "matrWater.ttl"));
        map.put(IRI.create("http://sweetontology.net/phen"), IRI.create(sweet + "phen.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmo"),
            IRI.create(sweet + "phenAtmo.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoCloud"),
            IRI.create(sweet + "phenAtmoCloud.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoFog"),
            IRI.create(sweet + "phenAtmoFog.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoFront"),
            IRI.create(sweet + "phenAtmoFront.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoLightning"),
            IRI.create(sweet + "phenAtmoLightning.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoPrecipitation"),
            IRI.create(sweet + "phenAtmoPrecipitation.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoPressure"),
            IRI.create(sweet + "phenAtmoPressure.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoSky"),
            IRI.create(sweet + "phenAtmoSky.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoTransport"),
            IRI.create(sweet + "phenAtmoTransport.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoWind"),
            IRI.create(sweet + "phenAtmoWind.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenAtmoWindMesoscale"),
            IRI.create(sweet + "phenAtmoWindMesoscale.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenBiol"),
            IRI.create(sweet + "phenBiol.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenCryo"),
            IRI.create(sweet + "phenCryo.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenCycle"),
            IRI.create(sweet + "phenCycle.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenCycleMaterial"),
            IRI.create(sweet + "phenCycleMaterial.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenEcology"),
            IRI.create(sweet + "phenEcology.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenElecMag"),
            IRI.create(sweet + "phenElecMag.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenEnergy"),
            IRI.create(sweet + "phenEnergy.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenEnvirImpact"),
            IRI.create(sweet + "phenEnvirImpact.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenFluidDynamics"),
            IRI.create(sweet + "phenFluidDynamics.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenFluidInstability"),
            IRI.create(sweet + "phenFluidInstability.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenFluidTransport"),
            IRI.create(sweet + "phenFluidTransport.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenGeol"),
            IRI.create(sweet + "phenGeol.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenGeolFault"),
            IRI.create(sweet + "phenGeolFault.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenGeolGeomorphology"),
            IRI.create(sweet + "phenGeolGeomorphology.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenGeolSeismicity"),
            IRI.create(sweet + "phenGeolSeismicity.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenGeolTectonic"),
            IRI.create(sweet + "phenGeolTectonic.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenGeolVolcano"),
            IRI.create(sweet + "phenGeolVolcano.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenHelio"),
            IRI.create(sweet + "phenHelio.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenHydro"),
            IRI.create(sweet + "phenHydro.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenMixing"),
            IRI.create(sweet + "phenMixing.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenOcean"),
            IRI.create(sweet + "phenOcean.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenOceanCoastal"),
            IRI.create(sweet + "phenOceanCoastal.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenOceanDynamics"),
            IRI.create(sweet + "phenOceanDynamics.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenPlanetClimate"),
            IRI.create(sweet + "phenPlanetClimate.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenReaction"),
            IRI.create(sweet + "phenReaction.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenSolid"),
            IRI.create(sweet + "phenSolid.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenStar"),
            IRI.create(sweet + "phenStar.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenSystem"),
            IRI.create(sweet + "phenSystem.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenSystemComplexity"),
            IRI.create(sweet + "phenSystemComplexity.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenWave"),
            IRI.create(sweet + "phenWave.ttl"));
        map.put(IRI.create("http://sweetontology.net/phenWaveNoise"),
            IRI.create(sweet + "phenWaveNoise.ttl"));
        map.put(IRI.create("http://sweetontology.net/proc"), IRI.create(sweet + "proc.ttl"));
        map.put(IRI.create("http://sweetontology.net/procChemical"),
            IRI.create(sweet + "procChemical.ttl"));
        map.put(IRI.create("http://sweetontology.net/procPhysical"),
            IRI.create(sweet + "procPhysical.ttl"));
        map.put(IRI.create("http://sweetontology.net/procStateChange"),
            IRI.create(sweet + "procStateChange.ttl"));
        map.put(IRI.create("http://sweetontology.net/procWave"),
            IRI.create(sweet + "procWave.ttl"));
        map.put(IRI.create("http://sweetontology.net/prop"), IRI.create(sweet + "prop.ttl"));
        map.put(IRI.create("http://sweetontology.net/propBinary"),
            IRI.create(sweet + "propBinary.ttl"));
        map.put(IRI.create("http://sweetontology.net/propCapacity"),
            IRI.create(sweet + "propCapacity.ttl"));
        map.put(IRI.create("http://sweetontology.net/propCategorical"),
            IRI.create(sweet + "propCategorical.ttl"));
        map.put(IRI.create("http://sweetontology.net/propCharge"),
            IRI.create(sweet + "propCharge.ttl"));
        map.put(IRI.create("http://sweetontology.net/propChemical"),
            IRI.create(sweet + "propChemical.ttl"));
        map.put(IRI.create("http://sweetontology.net/propConductivity"),
            IRI.create(sweet + "propConductivity.ttl"));
        map.put(IRI.create("http://sweetontology.net/propCount"),
            IRI.create(sweet + "propCount.ttl"));
        map.put(IRI.create("http://sweetontology.net/propDifference"),
            IRI.create(sweet + "propDifference.ttl"));
        map.put(IRI.create("http://sweetontology.net/propDiffusivity"),
            IRI.create(sweet + "propDiffusivity.ttl"));
        map.put(IRI.create("http://sweetontology.net/propDimensionlessRatio"),
            IRI.create(sweet + "propDimensionlessRatio.ttl"));
        map.put(IRI.create("http://sweetontology.net/propEnergy"),
            IRI.create(sweet + "propEnergy.ttl"));
        map.put(IRI.create("http://sweetontology.net/propEnergyFlux"),
            IRI.create(sweet + "propEnergyFlux.ttl"));
        map.put(IRI.create("http://sweetontology.net/propFraction"),
            IRI.create(sweet + "propFraction.ttl"));
        map.put(IRI.create("http://sweetontology.net/propFunction"),
            IRI.create(sweet + "propFunction.ttl"));
        map.put(IRI.create("http://sweetontology.net/propIndex"),
            IRI.create(sweet + "propIndex.ttl"));
        map.put(IRI.create("http://sweetontology.net/propMass"),
            IRI.create(sweet + "propMass.ttl"));
        map.put(IRI.create("http://sweetontology.net/propMassFlux"),
            IRI.create(sweet + "propMassFlux.ttl"));
        map.put(IRI.create("http://sweetontology.net/propOrdinal"),
            IRI.create(sweet + "propOrdinal.ttl"));
        map.put(IRI.create("http://sweetontology.net/propPressure"),
            IRI.create(sweet + "propPressure.ttl"));
        map.put(IRI.create("http://sweetontology.net/propQuantity"),
            IRI.create(sweet + "propQuantity.ttl"));
        map.put(IRI.create("http://sweetontology.net/propRotation"),
            IRI.create(sweet + "propRotation.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpace"),
            IRI.create(sweet + "propSpace.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpaceDirection"),
            IRI.create(sweet + "propSpaceDirection.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpaceDistance"),
            IRI.create(sweet + "propSpaceDistance.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpaceHeight"),
            IRI.create(sweet + "propSpaceHeight.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpaceLocation"),
            IRI.create(sweet + "propSpaceLocation.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpaceMultidimensional"),
            IRI.create(sweet + "propSpaceMultidimensional.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpaceThickness"),
            IRI.create(sweet + "propSpaceThickness.ttl"));
        map.put(IRI.create("http://sweetontology.net/propSpeed"),
            IRI.create(sweet + "propSpeed.ttl"));
        map.put(IRI.create("http://sweetontology.net/propTemperature"),
            IRI.create(sweet + "propTemperature.ttl"));
        map.put(IRI.create("http://sweetontology.net/propTemperatureGradient"),
            IRI.create(sweet + "propTemperatureGradient.ttl"));
        map.put(IRI.create("http://sweetontology.net/propTime"),
            IRI.create(sweet + "propTime.ttl"));
        map.put(IRI.create("http://sweetontology.net/propTimeFrequency"),
            IRI.create(sweet + "propTimeFrequency.ttl"));
        map.put(IRI.create("http://sweetontology.net/realm"), IRI.create(sweet + "realm.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmAstroBody"),
            IRI.create(sweet + "realmAstroBody.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmAstroHelio"),
            IRI.create(sweet + "realmAstroHelio.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmAstroStar"),
            IRI.create(sweet + "realmAstroStar.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmAtmo"),
            IRI.create(sweet + "realmAtmo.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmAtmoBoundaryLayer"),
            IRI.create(sweet + "realmAtmoBoundaryLayer.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmAtmoWeather"),
            IRI.create(sweet + "realmAtmoWeather.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmBiolBiome"),
            IRI.create(sweet + "realmBiolBiome.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmClimateZone"),
            IRI.create(sweet + "realmClimateZone.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmCryo"),
            IRI.create(sweet + "realmCryo.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmEarthReference"),
            IRI.create(sweet + "realmEarthReference.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmGeol"),
            IRI.create(sweet + "realmGeol.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmGeolBasin"),
            IRI.create(sweet + "realmGeolBasin.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmGeolConstituent"),
            IRI.create(sweet + "realmGeolConstituent.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmGeolContinental"),
            IRI.create(sweet + "realmGeolContinental.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmGeolOceanic"),
            IRI.create(sweet + "realmGeolOceanic.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmGeolOrogen"),
            IRI.create(sweet + "realmGeolOrogen.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmHydro"),
            IRI.create(sweet + "realmHydro.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmHydroBody"),
            IRI.create(sweet + "realmHydroBody.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandAeolian"),
            IRI.create(sweet + "realmLandAeolian.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandCoastal"),
            IRI.create(sweet + "realmLandCoastal.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandFluvial"),
            IRI.create(sweet + "realmLandFluvial.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandGlacial"),
            IRI.create(sweet + "realmLandGlacial.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandOrographic"),
            IRI.create(sweet + "realmLandOrographic.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandProtected"),
            IRI.create(sweet + "realmLandProtected.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandTectonic"),
            IRI.create(sweet + "realmLandTectonic.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandVolcanic"),
            IRI.create(sweet + "realmLandVolcanic.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmLandform"),
            IRI.create(sweet + "realmLandform.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmOcean"),
            IRI.create(sweet + "realmOcean.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmOceanFeature"),
            IRI.create(sweet + "realmOceanFeature.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmOceanFloor"),
            IRI.create(sweet + "realmOceanFloor.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmRegion"),
            IRI.create(sweet + "realmRegion.ttl"));
        map.put(IRI.create("http://sweetontology.net/realmSoil"),
            IRI.create(sweet + "realmSoil.ttl"));
        map.put(IRI.create("http://sweetontology.net/rela"), IRI.create(sweet + "rela.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaChemical"),
            IRI.create(sweet + "relaChemical.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaClimate"),
            IRI.create(sweet + "relaClimate.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaHuman"),
            IRI.create(sweet + "relaHuman.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaMath"),
            IRI.create(sweet + "relaMath.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaPhysical"),
            IRI.create(sweet + "relaPhysical.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaProvenance"),
            IRI.create(sweet + "relaProvenance.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaSci"), IRI.create(sweet + "relaSci.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaSpace"),
            IRI.create(sweet + "relaSpace.ttl"));
        map.put(IRI.create("http://sweetontology.net/relaTime"),
            IRI.create(sweet + "relaTime.ttl"));
        map.put(IRI.create("http://sweetontology.net/repr"), IRI.create(sweet + "repr.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataFormat"),
            IRI.create(sweet + "reprDataFormat.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataModel"),
            IRI.create(sweet + "reprDataModel.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataProduct"),
            IRI.create(sweet + "reprDataProduct.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataService"),
            IRI.create(sweet + "reprDataService.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataServiceAnalysis"),
            IRI.create(sweet + "reprDataServiceAnalysis.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataServiceGeospatial"),
            IRI.create(sweet + "reprDataServiceGeospatial.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataServiceReduction"),
            IRI.create(sweet + "reprDataServiceReduction.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprDataServiceValidation"),
            IRI.create(sweet + "reprDataServiceValidation.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMath"),
            IRI.create(sweet + "reprMath.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMathFunction"),
            IRI.create(sweet + "reprMathFunction.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMathFunctionOrthogonal"),
            IRI.create(sweet + "reprMathFunctionOrthogonal.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMathGraph"),
            IRI.create(sweet + "reprMathGraph.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMathOperation"),
            IRI.create(sweet + "reprMathOperation.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMathSolution"),
            IRI.create(sweet + "reprMathSolution.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprMathStatistics"),
            IRI.create(sweet + "reprMathStatistics.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciComponent"),
            IRI.create(sweet + "reprSciComponent.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciFunction"),
            IRI.create(sweet + "reprSciFunction.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciLaw"),
            IRI.create(sweet + "reprSciLaw.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciMethodology"),
            IRI.create(sweet + "reprSciMethodology.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciModel"),
            IRI.create(sweet + "reprSciModel.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciProvenance"),
            IRI.create(sweet + "reprSciProvenance.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSciUnits"),
            IRI.create(sweet + "reprSciUnits.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSpace"),
            IRI.create(sweet + "reprSpace.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSpaceCoordinate"),
            IRI.create(sweet + "reprSpaceCoordinate.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSpaceDirection"),
            IRI.create(sweet + "reprSpaceDirection.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSpaceGeometry"),
            IRI.create(sweet + "reprSpaceGeometry.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSpaceGeometry3D"),
            IRI.create(sweet + "reprSpaceGeometry3D.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprSpaceReferenceSystem"),
            IRI.create(sweet + "reprSpaceReferenceSystem.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprTime"),
            IRI.create(sweet + "reprTime.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprTimeDay"),
            IRI.create(sweet + "reprTimeDay.ttl"));
        map.put(IRI.create("http://sweetontology.net/reprTimeSeason"),
            IRI.create(sweet + "reprTimeSeason.ttl"));
        map.put(IRI.create("http://sweetontology.net/state"), IRI.create(sweet + "state.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateBiological"),
            IRI.create(sweet + "stateBiological.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateChemical"),
            IRI.create(sweet + "stateChemical.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateDataProcessing"),
            IRI.create(sweet + "stateDataProcessing.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateEnergyFlux"),
            IRI.create(sweet + "stateEnergyFlux.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateFluid"),
            IRI.create(sweet + "stateFluid.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateOrdinal"),
            IRI.create(sweet + "stateOrdinal.ttl"));
        map.put(IRI.create("http://sweetontology.net/statePhysical"),
            IRI.create(sweet + "statePhysical.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRealm"),
            IRI.create(sweet + "stateRealm.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRole"),
            IRI.create(sweet + "stateRole.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRoleBiological"),
            IRI.create(sweet + "stateRoleBiological.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRoleChemical"),
            IRI.create(sweet + "stateRoleChemical.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRoleGeographic"),
            IRI.create(sweet + "stateRoleGeographic.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRoleImpact"),
            IRI.create(sweet + "stateRoleImpact.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRoleRepresentative"),
            IRI.create(sweet + "stateRoleRepresentative.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateRoleTrust"),
            IRI.create(sweet + "stateRoleTrust.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSolid"),
            IRI.create(sweet + "stateSolid.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSpace"),
            IRI.create(sweet + "stateSpace.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSpaceConfiguration"),
            IRI.create(sweet + "stateSpaceConfiguration.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSpaceScale"),
            IRI.create(sweet + "stateSpaceScale.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSpectralBand"),
            IRI.create(sweet + "stateSpectralBand.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSpectralLine"),
            IRI.create(sweet + "stateSpectralLine.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateStorm"),
            IRI.create(sweet + "stateStorm.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateSystem"),
            IRI.create(sweet + "stateSystem.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateThermodynamic"),
            IRI.create(sweet + "stateThermodynamic.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateTime"),
            IRI.create(sweet + "stateTime.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateTimeCycle"),
            IRI.create(sweet + "stateTimeCycle.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateTimeFrequency"),
            IRI.create(sweet + "stateTimeFrequency.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateTimeGeologic"),
            IRI.create(sweet + "stateTimeGeologic.ttl"));
        map.put(IRI.create("http://sweetontology.net/stateVisibility"),
            IRI.create(sweet + "stateVisibility.ttl"));
        map.put(IRI.create("http://sweetontology.net/sweetAll"),
            IRI.create(sweet + "sweetAll.ttl"));

        return ontologyIRI -> map.get(ontologyIRI);
    }
}
