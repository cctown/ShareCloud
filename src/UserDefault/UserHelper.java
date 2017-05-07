package UserDefault;

import java.io.File;

import javax.swing.JOptionPane;

import com.KGCServer;

import SecretCloudProxy.CommonDef;
import SecretCloudProxy.PublicKey;
import encryption.CommonFileManager;
import encryption.KeyGen;
import encryption.encryptionModule;
import it.unisa.dia.gas.jpbc.Element;

public class UserHelper {
	Element dA;
	Element skA;
	PublicKey pkA;
	encryptionModule module;
	String paramsPath = "/Users/chencaixia/SecretCloud/params/";

	public static boolean checkUserInfo(String id) {
		String paramsPath = UserInfo.paramsPath + CommonDef.paramsAffix;
		File dir = new File(paramsPath);
		if (!dir.exists()) { // 公开参数文件不存在
			if (!KGCServer.getParams()) {
				return false;
			}
		}

		String partKeyPath = UserInfo.paramsPath + CommonDef.partKeyAffix(id);
		dir = new File(partKeyPath);
		if (!dir.exists()) { // 部分私钥文件不存在
			if (!KGCServer.getPartKey(id)) {
				return false;
			}
		}

		String skPath = UserInfo.paramsPath + CommonDef.secretKeyAffix(id);
		dir = new File(skPath);
		if (!dir.exists()) { // 私钥文件不存在
			encryptionModule module;
			try {
				module = new encryptionModule();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			byte[] partKey;
			try {
				partKey = CommonFileManager.getBytesFromFilepath(paramsPath + CommonDef.partKeyAffix(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			Element d = module.newG1ElementFromBytes(partKey).getImmutable();
			KeyGen.skpkGen(module, id, d);
		}
		return true;
	}
}
