<?php
namespace Group\Controller;

use Zend\Mvc\Controller\AbstractRestfulController;
use Group\Model\Group;
use Zend\View\Model\JsonModel;

class GroupController extends AbstractRestfulController
{
    protected $groupTable;

    public function getGroupTable()
    {
        if (!$this->groupTable) {
            $sm = $this->getServiceLocator();
            $this->groupTable = $sm->get('Group\Model\GroupTable');
        }
        return $this->groupTable;
    }

    public function getList()
    {
        $groups = $this->getGroupTable()->fetchAll();
        $variables = array();
        $json = new JsonModel( );
        foreach($groups as $album){
             array_push($variables,$album);
        }
        $json->setVariables($variables);
        return $json;
    }

    public function get($id)
    {
    }

    public function create($data)
    {
        $data['id']=0;
        $album = new Group();
        //TODO validation
        $album->exchangeArray($data);
        $id = $this->getGroupTable()->saveGroup($album);
        return new JsonModel(array(
            'group' => $id,
        ));
    }

    public function update($id, $data)
    {
        $data['id'] = $id;
        $newGroup = new Group();
        //TODO validation
        $newGroup->exchangeArray($data);
        $id = $this->getGroupTable()->saveGroup($newGroup);

        return new JsonModel(array(
            'group' => $id,
        ));
    }

    public function delete($id)
    {
        if($this->getGroupTable()->deleteGroup($id) > 0){
            return new JsonModel(array(
                'data' => 'deleted',
            ));
        }
        return new JsonModel(array(
            'data' => 'error',
        ));
    }
}