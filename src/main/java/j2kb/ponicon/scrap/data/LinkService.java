package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.dto.*;
import j2kb.ponicon.scrap.domain.Link;

public interface LinkService {
    public PostDataSaveRes linkSave(PostUrlReq postUrlReq, Long userId, Long categoryId) throws Exception;

    public GetDataListRes links(Long userId, Long categoryId, String seq);

    public GetDataListRes allLinks(Long userId);

    public void deleteLink(Long userId, Long linkId);
    public PatchLinkRes updateLink(Long userId, Long linkId, PatchLinkReq patchLinkReq);
}
