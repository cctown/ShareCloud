package UserDefault;

import java.io.File;

import com.KGCServer;

import SecretCloudProxy.CommonDef;
import SecretCloudProxy.CommonFileManager;
import SecretCloudProxy.PublicKey;
import encryption.KeyGen;
import encryption.encryptionModule;
import it.unisa.dia.gas.jpbc.Element;

public class UserHelper {
	Element dA;
	Element skA;
	PublicKey pkA;
	encryptionModule module;

	public static boolean checkUserInfo(String id) {
		String partKeyPath = UserInfo.getInstance().getParamsPath() + CommonDef.partKeyAffix(id);
		File dir = new File(partKeyPath);
		if (!dir.exists()) { // 部分私钥文件不存在
			if (!KGCServer.getPartKey(id)) {
				return false;
			}
		}

		String skPath = UserInfo.getInstance().getSecretKeyPath() + CommonDef.secretKeyAffix(id);
		String pkPath = CommonDef.pkPath + CommonDef.publicKeyAffix(id);
		File skFile = new File(skPath);
		File pkFile = new File(pkPath);
		if (!skFile.exists() || !pkFile.exists()) { // 私钥或公钥文件不存在
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
				partKey = CommonFileManager.getBytesFromFilepath(UserInfo.getInstance().getParamsPath() + CommonDef.partKeyAffix(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			Element d = module.newG1ElementFromBytes(partKey).getImmutable();
			try {
				KeyGen.skpkGen(module, id, d);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
}
