package brut.androlib.res.decoder;

import brut.androlib.exceptions.AndrolibException;
import brut.androlib.res.data.ResPackage;
import brut.androlib.res.data.ResTable;

import java.util.logging.Logger;

public class ARSCData {

    public ARSCData(ResPackage[] packages, ARSCDecoder.FlagsOffset[] flagsOffsets, ResTable resTable) {
        mPackages = packages;
        mFlagsOffsets = flagsOffsets;
        mResTable = resTable;
    }

    public ARSCDecoder.FlagsOffset[] getFlagsOffsets() {
        return mFlagsOffsets;
    }

    public ResPackage[] getPackages() {
        return mPackages;
    }

    public ResPackage getOnePackage() throws AndrolibException {
        if (mPackages.length <= 0) {
            throw new AndrolibException("Arsc file contains zero packages");
        } else if (mPackages.length != 1) {
            int id = findPackageWithMostResSpecs();
            LOGGER.info("Arsc file contains multiple packages. Using package "
                + mPackages[id].getName() + " as default.");

            return mPackages[id];
        }
        return mPackages[0];
    }

    public int findPackageWithMostResSpecs() {
        int count = mPackages[0].getResSpecCount();
        int id = 0;

        for (int i = 0; i < mPackages.length; i++) {
            if (mPackages[i].getResSpecCount() >= count) {
                count = mPackages[i].getResSpecCount();
                id = i;
            }
        }
        return id;
    }

    public ResTable getResTable() {
        return mResTable;
    }

    private static final Logger LOGGER = Logger.getLogger(ARSCData.class.getName());
    private final ResPackage[] mPackages;
    private final ARSCDecoder.FlagsOffset[] mFlagsOffsets;
    private final ResTable mResTable;
}
