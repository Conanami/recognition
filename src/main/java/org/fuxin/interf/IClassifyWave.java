package org.fuxin.interf;

import java.io.File;
import java.util.ArrayList;

import org.fuxin.caller.StandFile;
import org.fuxin.caller.WaveFileResult;

public interface IClassifyWave {
	

	public WaveFileResult Filter(String phonenumber, File file,
			ArrayList<StandFile> standlist);
}
